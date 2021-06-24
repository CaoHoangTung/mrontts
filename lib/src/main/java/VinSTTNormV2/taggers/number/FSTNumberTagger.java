package VinSTTNormV2.taggers.number;

import VinSTTNormV2.taggers.BaseTagger;
import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.utilities.ConfigUtilities;
import VinSTTNormV2.utilities.fst.FiniteStateTranducer;
import VinSTTNormV2.utilities.fst.GraphNode;
import VinSTTNormV2.utilities.fst.TraverseState;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

abstract public class FSTNumberTagger extends BaseTagger {
    private static String TAG = "NUMBER_ENTITY";

    protected final int EXCEPTION_WINDOW_SIZE = 4;

    protected final String ONE_DIGIT_TAG = "<1_digit>";
    protected final String TWO_DIGIT_TAG = "<2_digit>";
    protected final String THREE_DIGIT_TAG = "<3_digit>";
    protected final String FOUR_DIGIT_TAG = "<4_digit>";
    protected final String FIVE_DIGIT_TAG = "<5_digit>";
    protected final String SIX_DIGIT_TAG = "<6_digit>";
    protected final String SEVEN_DIGIT_TAG = "<7_digit>";
    protected final String EIGHT_DIGIT_TAG = "<8_digit>";
    protected final String NINE_DIGIT_TAG = "<9_digit>";
    protected final String MORE_THAN_NINE_DIGIT_TAG = "<more_than_9_digit>";

    // config
    protected JSONArray exceptionPatternJSONArray;
    protected HashSet<String> positiveExceptions;
    protected HashSet<String> negativeExceptions;
    protected JSONObject fstConfig;
    protected FiniteStateTranducer fst;

    public FSTNumberTagger(JSONObject config){
        super(config);
        this.init();
    }

    abstract public String getNumberType();

    public void init(){
        try {
            String[] fstConfigKeys = {this.getType(), "fst_config"};
            this.fstConfig = ConfigUtilities.getConfigItem(this.getGlobalConfig(), fstConfigKeys);

            this.fst = new FiniteStateTranducer(this.fstConfig.getJSONArray(getNumberType()));

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
    public String getType(){
        return "number";
    }

    protected String getTag(String token){
        try{
            int tokenHash = Integer.valueOf(token); // if token is not a normalized number, throw exception and return itself
            int tokenLength = token.length();

            switch (tokenLength) {
                case 1:
                    return ONE_DIGIT_TAG;
                case 2:
                    return TWO_DIGIT_TAG;
                case 3:
                    return THREE_DIGIT_TAG;
                case 4:
                    return FOUR_DIGIT_TAG;
                case 5:
                    return FIVE_DIGIT_TAG;
                case 6:
                    return SIX_DIGIT_TAG;
                case 7:
                    return SEVEN_DIGIT_TAG;
                case 8:
                    return EIGHT_DIGIT_TAG;
                case 9:
                    return NINE_DIGIT_TAG;
                default:
                    return MORE_THAN_NINE_DIGIT_TAG;
            }
        } catch (Exception e) {
            return token;
        }
    }

    protected ArrayList<SpanObject> chooseEntities(ArrayList<SpanObject> listA, ArrayList<SpanObject> listB) {
        int leftMostA = 999999999;
        int rightMostA = 0;
        int leftMostB = 999999999;
        int rightMostB = 0;

        for (SpanObject a : listA) {
            leftMostA = Math.min(leftMostA, a.characterStart);
            rightMostA = Math.max(rightMostA, a.characterEnd);
        }

        for (SpanObject b : listB) {
            leftMostB = Math.min(leftMostB, b.characterStart);
            rightMostB = Math.max(rightMostB, b.characterEnd);
        }

        if (leftMostA < leftMostB) {
            return listA;
        } else if (leftMostA == leftMostB) {
            if (rightMostA >= rightMostB) {
                return listA;
            } else {
                return listB;
            }
        } else {
            return listB;
        }
    }

    /**
     * Get left to right FST Matching
     * @param text
     * @return
     */
    private ArrayList<SpanObject> getLeftToRightFSTMatching(String text) {
        String[] tokens = text.split(" ");
        ArrayList<SpanObject> resultLeftToRight = new ArrayList<>();

        int tokenIdx = 0;
        int characterStart = 0;
        int characterEnd = -2;
        while (tokenIdx < tokens.length) {
            int currentTokenIdx = tokenIdx;
            int prevTokenIdx = -1;
            int nextTokenIdx = tokenIdx + 1;
            int nextCharacterStart = characterStart;

            String currentState = "";
            GraphNode currentNode = this.fst.getStartNode();

            SpanObject entityToAdd = null;
            while (currentTokenIdx < tokens.length) {
                String currentToken = tokens[currentTokenIdx];

                TraverseState nextState = this.fst.getNextState(currentNode, currentState, this.getTag(currentToken));
                // if cannot find by tag, find by exact token
                if (nextState == null) {
                    nextState = this.fst.getNextState(currentNode, currentState, currentToken);
                }

                // if next state is available, follow it
                if (nextState != null) {
                    GraphNode nextNode = nextState.node;
                    currentState = this.processState(currentToken, nextState.currentState);

                    currentNode = nextNode;
                    currentTokenIdx += nextState.positionalAction;

                    if (prevTokenIdx != currentTokenIdx)
                        characterEnd += currentToken.length() + 1;
                    prevTokenIdx = currentTokenIdx;

                    // if current segment is a number
                    if (currentNode.isEndState()) {
                        String[] prefixTokens = text.substring(0, characterStart).split(" ");
                        String[] postfixTokens = text.substring(Math.min(text.length(), characterEnd + 2)).split(" "); // postfixToken would be [token1, token2...]
                        String subText = text.substring(characterStart, characterEnd + 1);

                        // if the number is not an exception, move the end token position to the previous of the current segment
                        if (!this.isException(subText, prefixTokens, postfixTokens)) {
                            entityToAdd = (new SpanObject(characterStart, characterEnd, this.getType(), subText, currentState));
                            nextTokenIdx = currentTokenIdx;
                            nextCharacterStart = characterEnd + 2;
                        }
                        // else, continue the loop for the start token
                    }
                } else {
                    break;
                }
            }

            if (entityToAdd != null)
                resultLeftToRight.add(entityToAdd);


            // if cannot match any fst
            if (nextCharacterStart == characterStart) {
                nextCharacterStart += tokens[tokenIdx].length() + 1;
            }

            tokenIdx = nextTokenIdx;
            characterStart = nextCharacterStart;
            characterEnd = characterStart - 2;
        }

        return resultLeftToRight;
    }

    /**
     * Get right to left FST Matching
     * @param text
     * @return
     */
    private ArrayList<SpanObject> getRightToLeftFSTMatching(String text) {
        String[] tokens = text.split(" ");
        ArrayList<SpanObject> resultRightToLeft = new ArrayList<>();

        int endTokenIdx = tokens.length - 1;
        int characterStart;
        int characterEnd;

        while (endTokenIdx >= 0) {
            characterStart = 0;
            for (int startTokenIdx = 0 ; startTokenIdx <= endTokenIdx ; startTokenIdx++) {
                int currentTokenIdx = startTokenIdx;
                int prevTokenIdx = -1;
                characterEnd = characterStart - 2;

                String currentState = "";
                GraphNode currentNode = this.fst.getStartNode();

                // check fst
                while (currentTokenIdx <= endTokenIdx) {
                    String currentToken = tokens[currentTokenIdx];

                    if (prevTokenIdx != currentTokenIdx)
                        characterEnd += currentToken.length() + 1;
                    prevTokenIdx = currentTokenIdx;

                    TraverseState nextState = this.fst.getNextState(currentNode, currentState, this.getTag(currentToken));

                    // if cannot find by tag, find by exact token
                    if (nextState == null) {
                        nextState = this.fst.getNextState(currentNode, currentState, currentToken);
                    }

                    // if next state is available, follow it
                    if (nextState != null) {
                        GraphNode nextNode = nextState.node;
                        currentState = this.processState(currentToken, nextState.currentState);
                        currentNode = nextNode;
                        currentTokenIdx += nextState.positionalAction;
                    } else {
                        break;
                    }
                }

                // if current segment is a number
                if (currentNode.isEndState() && currentTokenIdx == endTokenIdx+1) {
                    String[] prefixTokens = text.substring(0, characterStart).split(" ");
                    String[] postfixTokens = text.substring(Math.min(text.length(), characterEnd + 2)).split(" "); // postfixToken would be [token1, token2...]
                    String subText = text.substring(characterStart, characterEnd+1);

                    // if the number is not an exception, move the end token position to the previous of the current segment
                    if (!this.isException(subText, prefixTokens, postfixTokens)) {
                        resultRightToLeft.add(new SpanObject(characterStart, characterEnd, this.getType(), subText, currentState));
                        endTokenIdx = startTokenIdx;
                        break;
                    }
                    // else, continue the loop for the start token
                }
                characterStart += tokens[startTokenIdx].length() + 1;
            }
            endTokenIdx--;
        }

        return resultRightToLeft;
    }

    @Override
    public SpanObject[] getSpans(String text){
        ArrayList<SpanObject> resultLeftToRight = getLeftToRightFSTMatching(text); // get span candidates with fst left to right
        ArrayList<SpanObject> resultRightToLeft = getRightToLeftFSTMatching(text); // get span candidates with fst right to left

        ArrayList<SpanObject> result = this.chooseEntities(resultLeftToRight, resultRightToLeft); // choose spans from the 2 direction list

        SpanObject[] resultStringArray = new SpanObject[result.size()];
        result.toArray(resultStringArray);

        return resultStringArray;
    }

    /**
     * Process the output state, replace special token such as <self>
     *
     * @param previousState
     * @param outputState
     * @return
     */
    protected String processState(String previousState, String outputState) {
        String result = outputState;

        result = result.replaceAll("<self>", previousState);

        return result;
    }

    protected String getSpanFromContext(String spokenFormEntity, String[] contextLeft, String[] contextRight, int spanLen, int leftContextSpanLen) {
        int rightContextSpanLen = spanLen - leftContextSpanLen - 1;

        StringBuilder spanStringBuilder = new StringBuilder();
        for (int leftContextIdx = Math.max(0, contextLeft.length - leftContextSpanLen); leftContextIdx < contextLeft.length; leftContextIdx++) {
            spanStringBuilder.append(contextLeft[leftContextIdx]).append(' ');
        }
        spanStringBuilder.append(spokenFormEntity).append(' ');
        for (int rightContextIdx = 0; rightContextIdx < Math.min(contextRight.length, rightContextSpanLen); rightContextIdx++) {
            spanStringBuilder.append(contextRight[rightContextIdx]).append(' ');
        }
        spanStringBuilder.setLength(spanStringBuilder.length() - 1);

        return spanStringBuilder.toString();
    }
}
