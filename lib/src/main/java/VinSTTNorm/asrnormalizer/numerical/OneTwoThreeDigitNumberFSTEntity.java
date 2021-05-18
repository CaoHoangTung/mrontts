package VinSTTNorm.asrnormalizer.numerical;

import org.json.JSONObject;

public class OneTwoThreeDigitNumberFSTEntity extends NumberFSTEntity {
    private static String TAG = "ONE_TWO_THREE_DIGIT_NUMBER_ENTITY";

    public OneTwoThreeDigitNumberFSTEntity(JSONObject config) {
        super(config);
    }

    @Override
    public String getNumberType() {
        return "one_two_three_digit";
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

            if (spokenFormEntity.equals("không") || spokenFormEntity.equals("tư")) {
                result = true;
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
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(TAG +  "Error isException.");
            return true;
        }
    }
}
