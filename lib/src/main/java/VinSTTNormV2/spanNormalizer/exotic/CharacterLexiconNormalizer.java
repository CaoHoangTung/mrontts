package VinSTTNormV2.spanNormalizer.exotic;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import VinSTTNormV2.spanExtractor.exotic.CharacterLexiconExtractor;
import VinSTTNormV2.spanNormalizer.ReplaceRegexNormalizer;
import org.json.JSONObject;

public class CharacterLexiconNormalizer extends ReplaceRegexNormalizer {
    private static String TAG = "character_lexicon_normalizer";

    public CharacterLexiconNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public ReplaceRegexExtractor getSampleExtractor(JSONObject config){
        return new CharacterLexiconExtractor(config);
    }
}
