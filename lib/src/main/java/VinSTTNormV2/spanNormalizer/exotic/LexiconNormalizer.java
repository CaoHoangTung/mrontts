package VinSTTNormV2.spanNormalizer.exotic;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import VinSTTNormV2.spanExtractor.exotic.LexiconExtractor;
import VinSTTNormV2.spanNormalizer.ReplaceRegexNormalizer;
import org.json.JSONObject;

public class LexiconNormalizer extends ReplaceRegexNormalizer {
    private static String TAG = "lexicon_normalizer";

    public LexiconNormalizer(JSONObject config){
        super(config);
    }
    @Override
    public ReplaceRegexExtractor getSampleExtractor(JSONObject config){
        return new LexiconExtractor(config);
    }
}
