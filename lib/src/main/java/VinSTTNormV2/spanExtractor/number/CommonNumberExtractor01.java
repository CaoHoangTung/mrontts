package VinSTTNormV2.spanExtractor.number;


import VinSTTNormV2.spanExtractor.SimpleRegexExtractor;
import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.utilities.ConfigUtilities;
import VinSTTNormV2.utilities.fst.FiniteStateTranducer;
import VinSTTNormV2.utilities.fst.GraphNode;
import VinSTTNormV2.utilities.fst.TraverseState;
import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class CommonNumberExtractor01 extends SimpleRegexExtractor {
    private static String TAG = "NUMBER_EXTRACTOR";

    private final int EXCEPTION_WINDOW_SIZE = 4;

    private JSONArray exceptionPatternJSONArray;
    private HashSet<String> positiveExceptions;
    private HashSet<String> negativeExceptions;
    private JSONObject fstConfig;
    private FiniteStateTranducer fst;

    public CommonNumberExtractor01(JSONObject config){
        super(config);
        this.init();
    }

    private void init(){
        try{
            String[] fstConfigKeys = {this.getType(), "fst_config"};
            this.fstConfig = ConfigUtilities.getConfigItem(this.getGlobalConfig(), fstConfigKeys);

            this.fst = new FiniteStateTranducer((this.fstConfig.getJSONArray("one_digit")));

            this.positiveExceptions = new HashSet<>();
            this.negativeExceptions = new HashSet<>();

            String[] exceptionKeys = {this.getType(), "exceptions"};
            JSONObject exceptionConfig = ConfigUtilities.getConfigItem(this.getGlobalConfig(), exceptionKeys);

            this.exceptionPatternJSONArray = (JSONArray) exceptionConfig.get("negative_patterns");

            JSONArray positiveExceptionsJSONArray = (JSONArray) exceptionConfig.get("positive_exceptions");
            for (int positiveExceptionsJSONArrayIdx = 0; positiveExceptionsJSONArrayIdx < positiveExceptionsJSONArray.length(); positiveExceptionsJSONArrayIdx++){
                String positiveException = positiveExceptionsJSONArray.get(positiveExceptionsJSONArrayIdx).toString();
                this.positiveExceptions.add(positiveException);
            }

            JSONArray negativeExceptionsJSONArray = (JSONArray) exceptionConfig.get("negative_exceptions");
            for (int negativeExceptionsJSONArrayIdx = 0; negativeExceptionsJSONArrayIdx< negativeExceptionsJSONArray.length(); negativeExceptionsJSONArrayIdx++){
                String negativeException = negativeExceptionsJSONArray.get(negativeExceptionsJSONArrayIdx).toString();
                this.negativeExceptions.add(negativeException);
                }
            } catch (Exception e){
            e.printStackTrace();
            System.out.println(TAG +  "Error CommonNumber init()");
        }
    }

    @Override
    public String getType(){
        return "number";
    }

    @Override
    public SpanObject[] getSpans(String text){
        ArrayList<SpanObject> result = new ArrayList<>();

        String[] tokens = text.split(" ");

        int characterStart = 0;
        int characterEnd = -2;
        int tokenIdx = 0;
        int prevTokenIdx = -1;
        String currentState = "";

        GraphNode currentNode = this.fst.getStartNode();

        while (tokenIdx < tokens.length){
            String token = tokens[tokenIdx];
            if (prevTokenIdx != tokenIdx) characterEnd += tokens.length + 1;
            prevTokenIdx = tokenIdx;

            TraverseState nextState = this.fst.getNextState(currentNode, currentState, token);
            if (nextState != null) {
                GraphNode nextNode = nextState.node;
                currentState = nextState.currentState;
                currentNode = nextNode;

                if (currentNode.isEndState()) {
                    String[] prefixTokens = text.substring(0, characterStart).split(" ");
                    String[] postfixTokens = text.substring(Math.min(text.length(), characterEnd + 2)).split(" ");
                    String subText = text.substring(characterStart, characterEnd + 1);

                    if (!this.isException(subText, prefixTokens, postfixTokens)) {
                        result.add(new SpanObject(characterStart, characterEnd, this.getType(), subText, currentState));
                    }
                }
                tokenIdx += 1;
            }else {
                // if current token is not a valid path, reset the fst and move on to the next token
                currentNode = this.fst.getStartNode();
                currentState = "";
                characterStart = characterEnd + 2;
                tokenIdx++;

            }
        }
        SpanObject[] resultStringArray = new SpanObject[result.size()];
        result.toArray(resultStringArray);

        return resultStringArray;
    }
}
