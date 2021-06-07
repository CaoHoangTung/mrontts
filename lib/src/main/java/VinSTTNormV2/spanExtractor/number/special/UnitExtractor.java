package VinSTTNormV2.spanExtractor.number.special;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import org.json.JSONObject;

public class UnitExtractor extends ReplaceRegexExtractor {
    private static String TAG = "unit_extractor";

    public UnitExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "unit";
    }
}
