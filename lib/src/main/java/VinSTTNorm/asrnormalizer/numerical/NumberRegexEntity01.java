package VinSTTNorm.asrnormalizer.numerical;


import VinSTTNorm.asrnormalizer.abstractentity.SimpleRegexEntity;
import VinSTTNorm.asrnormalizer.config.RegexConfig;
import VinSTTNorm.asrnormalizer.entityobject.EntityObject;
import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import VinSTTNorm.asrnormalizer.utilities.fst.FiniteStateTranducer;
import VinSTTNorm.asrnormalizer.utilities.fst.GraphNode;
import VinSTTNorm.asrnormalizer.utilities.fst.TraverseState;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberRegexEntity01 extends SimpleRegexEntity {

    private static String TAG = "NUMBER_ENTITY";

    private final int EXCEPTION_WINDOW_SIZE = 4; // the window size for exception checking

    // config
    private JSONArray exceptionPatternJSONArray;
    private HashSet<String> positiveExceptions;
    private HashSet<String> negativeExceptions;
    private JSONObject fstConfig;
    private FiniteStateTranducer fst;

    public NumberRegexEntity01(JSONObject config) {
        super(config);
        this.init();
    }

    public void init() {
        try {
            String[] fstConfigKeys = {this.getType(), "fst_config"};
            this.fstConfig = ConfigUtilities.getConfigItem(this.getGlobalConfig(), fstConfigKeys);

            this.fst = new FiniteStateTranducer(this.fstConfig.getJSONArray("one_digit"));

            this.positiveExceptions = new HashSet<>();
            this.negativeExceptions = new HashSet<>();

            String[] exceptionKeys = {this.getType(), "exceptions"};
            JSONObject exceptionConfig = ConfigUtilities.getConfigItem(this.getGlobalConfig(), exceptionKeys);

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
    public EntityObject[] getEntities(String text) {
        ArrayList<EntityObject> result = new ArrayList<>();

        String[] tokens = text.split(" ");

        int characterStart = 0;
        int characterEnd = -2;
        int tokenIdx = 0;
        int prevTokenIdx = -1;
        String currentState = "";
        GraphNode currentNode = this.fst.getStartNode();

        while (tokenIdx < tokens.length) {
            String token = tokens[tokenIdx];
            if (prevTokenIdx != tokenIdx)
                characterEnd += token.length() + 1;
            prevTokenIdx = tokenIdx;

            TraverseState nextState = this.fst.getNextState(currentNode, currentState, token);

            // if next state is available, follow it
            if (nextState != null) {
                GraphNode nextNode = nextState.node;
                currentState = nextState.currentState;
                currentNode = nextNode;

                if (currentNode.isEndState()) {

                    String[] prefixTokens = text.substring(0, characterStart).split(" ");
                    String[] postfixTokens = text.substring(Math.min(text.length(), characterEnd + 2)).split(" "); // postfixToken would be [token1, token2...]
                    String subText = text.substring(characterStart, characterEnd+1);

                    if (!this.isException(subText, prefixTokens, postfixTokens)) {
                        result.add(new EntityObject(characterStart, characterEnd, this.getType(), subText, currentState));

                    }
                }
                tokenIdx += nextState.positionalAction;

            } else {
                // if current token is not a valid path, reset the fst and move on to the next token
                currentNode = this.fst.getStartNode();
                currentState = "";
                characterStart = characterEnd + 2;
                tokenIdx++;
            }
        }

        EntityObject[] resultStringArray = new EntityObject[result.size()];
        result.toArray(resultStringArray);

        return resultStringArray;
    }

    @Override
    public String normEntity(String spokenFormEntityString) {
        return spokenFormEntityString;
    }

    /**
     * Smooth the written form text obtained from norm function
     * Ex: 20 19 => 2019 (year)
     * @param writtenFormText
     * @return
     */
    public String smoothWrittenForm(String writtenFormText) {
        String joinText = writtenFormText.replaceAll(" ", "");
        if (joinText.length() == 4 && (joinText.startsWith("20") || joinText.startsWith("1"))) {
            writtenFormText = joinText;
        }
        return writtenFormText;
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
                        System.out.println("SPAN " + currentSpan + " " + spokenFormEntity);
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
