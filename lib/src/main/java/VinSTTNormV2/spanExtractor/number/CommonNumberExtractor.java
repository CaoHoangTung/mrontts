package VinSTTNormV2.spanExtractor.number;

import VinSTTNorm.asrnormalizer.numerical.NumberRegexEntity;
import VinSTTNormV2.config.RegexConfig;
import VinSTTNormV2.spanExtractor.SimpleRegexExtractor;
import VinSTTNormV2.utilities.ConfigUtilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonNumberExtractor extends SimpleRegexExtractor {
    private static class Pair{
        public final int value;
        public final int exp;

        public Pair(int value, int exp){
            this.value = value;
            this.exp = exp;
        }

        public String toString(){
            return String.format("Pair(%s,%s", value, exp);
        }
    }

    private static String TAG = "NUMBER_EXTRACTOR";

    private final int EXCEPTION_WINDOW_SIZE = 4;

    private final HashMap<String, Pair> stringToDigit = new HashMap<>();
    private final HashMap<String, Pair> stringToExp = new HashMap<>();

    private JSONArray exceptionPatternJSONArray;
    private HashSet<String> positiveExceptions;
    private HashSet<String> negativeExceptions;

    public CommonNumberExtractor(JSONObject config){
        super(config);
        this.init();
        stringToDigit.put("linh", new Pair(0, 0));
        stringToDigit.put("lẻ", new Pair(0, 0));
        stringToDigit.put("không", new Pair(0, 0));
        stringToDigit.put("mốt", new Pair(1, 0));
        stringToDigit.put("một", new Pair(1, 0));
        stringToDigit.put("hai", new Pair(2, 0));
        stringToDigit.put("hăm", new Pair(2, 0));
        stringToDigit.put("ba", new Pair(3, 0));
        stringToDigit.put("bốn", new Pair(4, 0));
        stringToDigit.put("tư", new Pair(4, 0));
        stringToDigit.put("năm", new Pair(5, 0));
        stringToDigit.put("lăm", new Pair(5, 0));
        stringToDigit.put("nhăm", new Pair(5, 0));
        stringToDigit.put("sáu", new Pair(6, 0));
        stringToDigit.put("bảy", new Pair(7, 0));
        stringToDigit.put("bẩy", new Pair(7, 0));
        stringToDigit.put("tám", new Pair(8, 0));
        stringToDigit.put("chín", new Pair(9, 0));
        stringToDigit.put("mười", new Pair(1, 1));


        // the value here represent if the exp should be replaced(1) or multiplied(0) by the original exp from the stack
        stringToExp.put("tỉ", new Pair(1000000000, 9));
        stringToExp.put("tỷ", new Pair(1000000000, 9));
        stringToExp.put("triệu", new Pair(1000000, 6));
        stringToExp.put("nghìn", new Pair(1000, 3));
        stringToExp.put("ngàn", new Pair(1000, 3));
        stringToExp.put("trăm", new Pair(100, 2));
        stringToExp.put("chục", new Pair(10, 1));
        stringToExp.put("mươi", new Pair(10, 1));
    }

    public void init(){
        try{
            this.positiveExceptions = new HashSet<>();
            this.negativeExceptions = new HashSet<>();

            String[] keys = {this.getType(), "exceptions"};
            JSONObject exceptionConfig = ConfigUtilities.getConfigItem(this.getGlobalConfig(), keys);

            this.exceptionPatternJSONArray = (JSONArray) exceptionConfig.get("negative_patterns");

            JSONArray positiveExceptionsJSONArray = (JSONArray) exceptionConfig.get("positive_exceptions");
            for (int positiveExceptionsJSONArrayIdx = 0; positiveExceptionsJSONArrayIdx < positiveExceptionsJSONArray.length(); positiveExceptionsJSONArrayIdx++) {
                String positiveException = positiveExceptionsJSONArray.get(positiveExceptionsJSONArrayIdx).toString();
                this.positiveExceptions.add(positiveException);
            }

            JSONArray negativeExceptionsJSONArray = (JSONArray) exceptionConfig.get("negative_exceptions");
            for (int negativeExceptionsJSONArrayIdx = 0; negativeExceptionsJSONArrayIdx < negativeExceptionsJSONArray.length(); negativeExceptionsJSONArrayIdx++) {
                String negativeException = negativeExceptionsJSONArray.get(negativeExceptionsJSONArrayIdx).toString();
                this.negativeExceptions.add(negativeException);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(TAG +  "Error init()");
        }
    }
    @Override
    public String getType() {
        return "number";
    }

    @Override
    public boolean isException(String spokenFormEntity, String[] contextLeft, String[] contextRight) {
        try {
            boolean result = false;

            if (spokenFormEntity.equals("năm")) { // except case năm 2020, năm 2021, 1 năm, 10 năm...
                if (contextRight.length > 0) {
                    if (contextRight.length > 0 && contextRight[0].matches("[0-9]+"))
                        return true;
                    if (contextLeft.length > 0 && contextLeft[contextLeft.length-1].matches("[0-9]+"))
                        return true;
                }
            }

            for (int i = 0 ; i < this.exceptionPatternJSONArray.length() ; i++) {
                RegexConfig exceptionRegexConfig;
                exceptionRegexConfig = ConfigUtilities.getRegexConfigFromJSONObject(this.exceptionPatternJSONArray.getJSONObject(i));

                exceptionRegexConfig.setPattern(this.processRegexConfigString(exceptionRegexConfig.getPattern()));

                String exceptionRegexString = exceptionRegexConfig.toString();
                Pattern exceptionPattern = Pattern.compile(exceptionRegexString);
                Matcher exceptionMatcher = exceptionPattern.matcher(spokenFormEntity);


                // if the entity match the negative_patterns, temporary assign true
                if (exceptionMatcher.find()) {
                    result = true;
                }

                // Now, iterate through every possible combination of tokens including the entity
                for (int spanLen = 2 ; spanLen <= this.EXCEPTION_WINDOW_SIZE ; spanLen++) {
                    for (int leftContextSpanLen = 0 ; leftContextSpanLen < spanLen ; leftContextSpanLen++) {
                        String currentSpan = this.getSpanFromContext(spokenFormEntity, contextLeft, contextRight, spanLen, leftContextSpanLen);

                        // with the current span, check if it should be normed

                        // if the entity string match negative_pattern
                        if (result == true) {
                            // if the entity string should be normed
                            if (positiveExceptions.contains(currentSpan)) {
                                return false;
                            }
                        } else if (negativeExceptions.contains(currentSpan)) {
                            return true;
                        }
                    }
                }
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(TAG +  "Error isException.");
            return true;
        }
    }
}
