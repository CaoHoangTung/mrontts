package VinSTTNormV2.spanExtractor.number.special;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import org.json.JSONObject;

public class NumberPunctuationExtractor extends ReplaceRegexExtractor {
    private final String TAG = "number_punctuation_extractor";

    public NumberPunctuationExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "number_punctuation";
    }
}
