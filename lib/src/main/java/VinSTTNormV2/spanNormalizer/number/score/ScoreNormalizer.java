package VinSTTNormV2.spanNormalizer.number.score;

import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import VinSTTNormV2.spanNormalizer.number.CommonNumberNormalizer;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ScoreNormalizer extends CommonNumberNormalizer {
    public final String TAG = "score_normalizer";

    public ScoreNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public String doNorm(String spokenFormEntityString){
        String[] tokens = spokenFormEntityString.split(" ");
        String normedText = "";

        if (tokens[tokens.length-1].equals("đều")){
            String[] numArray = Arrays.asList(tokens).subList(0, tokens.length - 1).toArray(new String[0]);
            String numText = String.join(" ", numArray);
            String num = super.doNorm(numText);
            normedText = num + "-" + num;
        }
        else if(tokens.length == 2){
            String num1 = super.doNorm(tokens[0]);
            String num2 = super.doNorm(tokens[1]);
            normedText = num1 + "-" + num2;
        }
        else{
            String num = super.doNorm(spokenFormEntityString);
            String[] nums = num.split(" ");
            normedText = String.join("-", nums);
        }
        return normedText;
    }
}
