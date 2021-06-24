package VinSTTNormV2.verbalizers.number.special;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import VinSTTNormV2.taggers.number.special.NumberPunctuationTagger;
import VinSTTNormV2.verbalizers.ReplaceRegexVerbalizer;
import org.json.JSONObject;

public class NumberPunctuationVerbalizer extends ReplaceRegexVerbalizer {
    private final String TAG = "number_punctuation_normalizer";

    @Override
    public ReplaceRegexTagger getSampleExtractor(JSONObject config) {
        return new NumberPunctuationTagger(config);
    }

    public NumberPunctuationVerbalizer(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "number_punctuation";
    }
}
