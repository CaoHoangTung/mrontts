package VinSTTNormV2.taggers.number.date;

import VinSTTNormV2.taggers.number.CommonNumberTagger;
import org.json.JSONObject;

public class YearTagger extends CommonNumberTagger {
    private static String TAG = "YEAR_ENTITY";

    public YearTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "year";
    }
}
