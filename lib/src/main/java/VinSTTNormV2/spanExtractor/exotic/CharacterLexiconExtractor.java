package VinSTTNormV2.spanExtractor.exotic;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import org.json.JSONObject;

public class CharacterLexiconExtractor extends ReplaceRegexExtractor {
    private static String TAG = "character_lexicon_extractor";

    public CharacterLexiconExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "character_lexicon";
    }
}
