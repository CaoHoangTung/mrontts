package VinSTTNormV2.taggers.number.special;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import org.json.JSONObject;

public class NumberPunctuationTagger extends ReplaceRegexTagger {
    private final String TAG = "number_punctuation_extractor";

    public NumberPunctuationTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "number_punctuation";
    }
}
