package VinSTTNormV2.spanExtractor.exotic;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import org.json.JSONObject;

public class LexiconExtractor extends ReplaceRegexExtractor {
    private static String TAG = "lexicon_extractor";

    public LexiconExtractor(JSONObject config){
        super(config);
    }
    @Override
    public String getType(){
        return "lexicon";
    }
}
