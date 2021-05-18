package VinSTTNorm.asrnormalizer.numerical;

import VinSTTNorm.asrnormalizer.BaseEntity;
import VinSTTNorm.asrnormalizer.entityobject.EntityObject;
import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import VinSTTNorm.asrnormalizer.utilities.fst.FiniteStateTranducer;
import VinSTTNorm.asrnormalizer.utilities.fst.GraphNode;
import VinSTTNorm.asrnormalizer.utilities.fst.TraverseState;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

abstract public class NumberFSTEntity extends BaseEntity {
    private static String TAG = "NUMBER_ENTITY";

    protected final int EXCEPTION_WINDOW_SIZE = 4; // the window size for exception checking

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

    public NumberFSTEntity(JSONObject config) {
        super(config);
        this.init();
    }

    abstract public String getNumberType();

    public void init() {
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
    public String getType() {
        return "number";
    }

    protected String getTag(String token) {
        try {
            int tokenHash = Integer.valueOf(token);
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

    protected ArrayList<EntityObject> chooseEntities(ArrayList<EntityObject> listA, ArrayList<EntityObject> listB) {
        int leftMostA = 999999999;
        int rightMostA = 0;
        int leftMostB = 999999999;
        int rightMostB = 0;

        for (EntityObject a : listA) {
            leftMostA = Math.min(leftMostA, a.characterStart);
            rightMostA = Math.max(rightMostA, a.characterEnd);
        }

        for (EntityObject b : listB) {
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

    @Override
    public EntityObject[] getEntities(String text) {
        ArrayList<EntityObject> resultLeftToRight = new ArrayList<>();
        ArrayList<EntityObject> resultRightToLeft = new ArrayList<>();

        String[] tokens = text.split(" ");

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

            EntityObject entityToAdd = null;
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
                            entityToAdd = (new EntityObject(characterStart, characterEnd, this.getType(), subText, currentState));
                            nextTokenIdx = currentTokenIdx;
                            nextCharacterStart = characterEnd + 2;
//                            System.out.println(String.format("ADD %s %s %s %s", characterStart, characterEnd, subText, this.normEntity(currentState)));
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

//        RIGHT TO LEFT MATCHING
        int endTokenIdx = tokens.length - 1;

        while (endTokenIdx >= 0) {
            characterStart = 0;
            for (int startTokenIdx = 0 ; startTokenIdx <= endTokenIdx ; startTokenIdx++) {
//                System.out.println(String.format("CHECKING TOKEN RANGE(%s %s) of %s", startTokenIdx, endTokenIdx, tokens.length));
                int currentIdx = startTokenIdx;
                int prevTokenIdx = -1;
                characterEnd = characterStart - 2;

                String currentState = "";
                GraphNode currentNode = this.fst.getStartNode();

                // check fst
                while (currentIdx <= endTokenIdx) {
                    String currentToken = tokens[currentIdx];

//                    System.out.println("  CURRENT TOKEN " + currentIdx + " " + currentToken);
                    if (prevTokenIdx != currentIdx)
                        characterEnd += currentToken.length() + 1;
                    prevTokenIdx = currentIdx;

                    TraverseState nextState = this.fst.getNextState(currentNode, currentState, this.getTag(currentToken));
                    // if cannot find by tag, find by exact token
                    if (nextState == null) {
                        nextState = this.fst.getNextState(currentNode, currentState, currentToken);
                    }
//                    if (nextState != null)
//                        System.out.println("NEXT STATE " + nextState.node.getNodeIdx());

                    // if next state is available, follow it
                    if (nextState != null) {
                        GraphNode nextNode = nextState.node;
                        currentState = this.processState(currentToken, nextState.currentState);
                        currentNode = nextNode;

                        currentIdx += nextState.positionalAction;
                    } else {
                        break;
                    }
                }

//                System.out.println("CURRENT NODE " + currentNode);
//                System.out.println("CURRENT IDX " + currentIdx + " " + endTokenIdx);

                // if current segment is a number
                if (currentNode.isEndState() && currentIdx == endTokenIdx+1) {
                    String[] prefixTokens = text.substring(0, characterStart).split(" ");
                    String[] postfixTokens = text.substring(Math.min(text.length(), characterEnd + 2)).split(" "); // postfixToken would be [token1, token2...]
                    String subText = text.substring(characterStart, characterEnd+1);

                    // if the number is not an exception, move the end token position to the previous of the current segment
                    if (!this.isException(subText, prefixTokens, postfixTokens)) {
                        resultRightToLeft.add(new EntityObject(characterStart, characterEnd, this.getType(), subText, currentState));
                        endTokenIdx = startTokenIdx;
//                        System.out.println(String.format("ADD %s %s %s %s", characterStart, characterEnd, subText, this.normEntity(currentState)));
                        break;
                    }
                    // else, continue the loop for the start token
                }
                characterStart += tokens[startTokenIdx].length() + 1;
            }
            endTokenIdx--;
        }

        ArrayList<EntityObject> result = this.chooseEntities(resultLeftToRight, resultRightToLeft);

        EntityObject[] resultStringArray = new EntityObject[result.size()];
        result.toArray(resultStringArray);

        return resultStringArray;
    }

    @Override
    public String normEntity(String spokenFormEntityString) {
        return this.smoothWrittenForm(spokenFormEntityString);
    }

    /**
     * Smooth the written form text obtained from norm function
     * Ex: 20 19 => 2019 (year)
     *
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

    /**
     * Get the span text combination from contextLeft, spokenFormEntity, contextRight
     *
     * @param spokenFormEntity
     * @param contextLeft
     * @param contextRight
     * @param spanLen
     * @param leftContextSpanLen
     * @return
     */
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

    @Override
    public boolean isException(String spokenFormEntity, String[] contextLeft, String[] contextRight) {
        return false;
    }
}
