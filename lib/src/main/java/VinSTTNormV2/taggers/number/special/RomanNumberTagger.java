package VinSTTNormV2.taggers.number.special;

import VinSTTNormV2.taggers.SimpleRegexTagger;
import org.json.JSONObject;

public class RomanNumberTagger extends SimpleRegexTagger {
    public final String TAG = "roman_number_extractor";

    public RomanNumberTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getType() {
        return "roman_number";
    }
}
