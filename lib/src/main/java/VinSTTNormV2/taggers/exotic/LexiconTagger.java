package VinSTTNormV2.taggers.exotic;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import org.json.JSONObject;

public class LexiconTagger extends ReplaceRegexTagger {
    private static String TAG = "lexicon_extractor";

    public LexiconTagger(JSONObject config){
        super(config);
    }
    @Override
    public String getType(){
        return "lexicon";
    }
}
