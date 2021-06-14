package VinSTTNormV2.spanExtractor.number.special;

import VinSTTNormV2.spanExtractor.SimpleRegexExtractor;
import org.json.JSONObject;

public class RomanNumberExtractor extends SimpleRegexExtractor {
    public final String TAG = "roman_number_extractor";

    public RomanNumberExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType() {
        return "roman_number";
    }
}
