package VinSTTNormV2.taggers.number.special;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import org.json.JSONObject;

public class UnitTagger extends ReplaceRegexTagger {
    private static String TAG = "unit_extractor";

    public UnitTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "unit";
    }
}
