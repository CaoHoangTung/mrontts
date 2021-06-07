package VinSTTNormV2.spanNormalizer.number.special;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import VinSTTNormV2.spanExtractor.number.special.NumberPunctuationExtractor;
import VinSTTNormV2.spanNormalizer.ReplaceRegexNormalizer;
import org.json.JSONObject;

public class NumberPunctuationNormalizer extends ReplaceRegexNormalizer {
    private final String TAG = "number_punctuation_normalizer";

    @Override
    public ReplaceRegexExtractor getSampleExtractor(JSONObject config) {
        return new NumberPunctuationExtractor(config);
    }

    public NumberPunctuationNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "number_punctuation";
    }
}
