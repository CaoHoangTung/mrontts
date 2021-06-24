package VinSTTNormV2.taggers.exotic;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import org.json.JSONObject;

public class CharacterLexiconTagger extends ReplaceRegexTagger {
    private static String TAG = "character_lexicon_extractor";

    public CharacterLexiconTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "character_lexicon";
    }
}
