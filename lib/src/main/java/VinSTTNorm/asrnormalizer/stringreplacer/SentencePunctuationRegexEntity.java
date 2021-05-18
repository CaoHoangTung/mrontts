package VinSTTNorm.asrnormalizer.stringreplacer;


import VinSTTNorm.asrnormalizer.abstractentity.ReplaceRegexEntity;
import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashSet;

public class SentencePunctuationRegexEntity extends ReplaceRegexEntity {
    private static String TAG = "NUMBER_PUNCTUATION_ENTITY";
    private static int EXCEPTION_WINDOW_SIZE = 4;
    private HashSet<String> negativeExceptions;

    public SentencePunctuationRegexEntity(JSONObject config) {
        super(config);
        this.init();
    }

    public void init() {
        try {
            this.negativeExceptions = new HashSet<>();
            String[] keys = {this.getType(), "exceptions"};
            JSONObject exceptionConfig = ConfigUtilities.getConfigItem(this.getGlobalConfig(), keys);

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
        return "sentence_punctuation";
    }

    @Override
    public boolean isException(String spokenFormEntity, String[] contextLeft, String[] contextRight) {
        try {
            // Now, iterate through every possible combination of tokens including the entity
            for (int spanLen = 2 ; spanLen <= this.EXCEPTION_WINDOW_SIZE ; spanLen++) {
                for (int leftContextSpanLen = 0 ; leftContextSpanLen < spanLen ; leftContextSpanLen++) {
                    String currentSpan = this.getSpanFromContext(spokenFormEntity, contextLeft, contextRight, spanLen, leftContextSpanLen);
                    // with the current span, check if it should be normed
                    if (negativeExceptions.contains(currentSpan)) {
                        return true;
                    }
                }
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(TAG +  "Error isException.");
            return true;
        }
    }
}
