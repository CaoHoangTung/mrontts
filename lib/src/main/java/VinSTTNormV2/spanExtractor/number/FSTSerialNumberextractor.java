package VinSTTNormV2.spanExtractor.number;

import org.json.JSONObject;

public class FSTSerialNumberextractor extends FSTNumberExtractor{

    private static String TAG = "SERIAL_DIGIT_NUMBER_EXTRACTOR";
    private final int MIN_TOKEN_COUNT = 7;

    public  FSTSerialNumberextractor(JSONObject config){
        super(config);
    }

    @Override
    public String getNumberType(){
        return "serial";
    }

    @Override
    public boolean isException(String spokenFormEntity, String[] contextLeft, String[] contextRight) {
        if (spokenFormEntity.split(" ").length < MIN_TOKEN_COUNT) {
            return true;
        }
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
                // Now, iterate through every possible combination of tokens including the entity
                for (int spanLen = 2 ; spanLen <= this.EXCEPTION_WINDOW_SIZE ; spanLen++) {
                    for (int leftContextSpanLen = 0 ; leftContextSpanLen < spanLen ; leftContextSpanLen++) {
                        String currentSpan = this.getSpanFromContext(spokenFormEntity, contextLeft, contextRight, spanLen, leftContextSpanLen);
                        // with the current span, check if it should be normed

                        // if the entity string match negative_pattern
                        if (positiveExceptions.contains(currentSpan)) {
                            return false;
                        } else if (negativeExceptions.contains(currentSpan)) {
                            return true;
                        }
                    }
                }
                return false;
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(TAG +  "Error isException.");
            return true;
        }
    }

}
