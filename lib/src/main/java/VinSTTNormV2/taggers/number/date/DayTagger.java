package VinSTTNormV2.taggers.number.date;

import VinSTTNormV2.taggers.number.CommonNumberTagger;
import org.json.JSONObject;

public class DayTagger extends CommonNumberTagger {
    private static String TAG = "day_extractor";

    public DayTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "day";
    }
}
