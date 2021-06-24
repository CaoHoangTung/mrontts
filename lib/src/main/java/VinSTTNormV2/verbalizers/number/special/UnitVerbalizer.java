package VinSTTNormV2.verbalizers.number.special;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import VinSTTNormV2.taggers.number.special.UnitTagger;
import VinSTTNormV2.verbalizers.ReplaceRegexVerbalizer;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Locale;

public class UnitVerbalizer extends ReplaceRegexVerbalizer {
    public final String TAG = "unit_normalizer";

    public boolean isNumeric(String s) {
        return s != null && s.matches("^[0-9].*");
    }

    @Override
    public ReplaceRegexTagger getSampleExtractor(JSONObject config) {
        return new UnitTagger(config);
    }

    public UnitVerbalizer(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "unit";
    }

    @Override
    public String doNorm(String spokenFormEntityString){
        String[] tokens = spokenFormEntityString.toLowerCase(Locale.ROOT).split(" ");

        if (isNumeric(tokens[0])){
            if (tokens[tokens.length - 1].equals("rưỡi")){
                String normedString = super.doNorm(String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length-1)));
                return tokens[0] + ".5" + " " +  normedString;
            }
            //1 mét 50
            if (isNumeric(tokens[tokens.length-1])){
                String normedString = super.doNorm(String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length-1)));
                return tokens[0] + normedString + tokens[tokens.length-1];
            }
            return tokens[0] + " " + super.doNorm(String.join(" ", Arrays.copyOfRange(tokens, 1, tokens.length)));
        }
        else{
            if (isNumeric(tokens[tokens.length-1])){
                String normedString = super.doNorm(String.join(" ", Arrays.copyOfRange(tokens, 0, tokens.length-1)));
//                System.out.println(normedString + tokens[tokens.length-1]);
                return normedString + tokens[tokens.length-1];
            }
            return super.doNorm(spokenFormEntityString);
        }
    }
}
