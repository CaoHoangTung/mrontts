package VinSTTNormV2.verbalizers.exotic;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import VinSTTNormV2.taggers.exotic.LexiconTagger;
import VinSTTNormV2.verbalizers.ReplaceRegexVerbalizer;
import org.json.JSONObject;

public class LexiconVerbalizer extends ReplaceRegexVerbalizer {
    private static String TAG = "lexicon_normalizer";

    public LexiconVerbalizer(JSONObject config){
        super(config);
    }
    @Override
    public ReplaceRegexTagger getSampleExtractor(JSONObject config){
        return new LexiconTagger(config);
    }
}
