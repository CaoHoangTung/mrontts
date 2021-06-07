package VinSTTNormV2.spanExtractor.number.date;

import VinSTTNormV2.spanExtractor.number.CommonNumberExtractor;
import org.json.JSONObject;

public class DayExtractor extends CommonNumberExtractor {
    private static String TAG = "day_extractor";

    public DayExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "day";
    }
}
