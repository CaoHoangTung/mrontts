package VinSTTNormV2.spanExtractor.number.date;

import VinSTTNormV2.spanExtractor.number.CommonNumberExtractor;
import org.json.JSONObject;

public class YearExtractor extends CommonNumberExtractor {
    private static String TAG = "YEAR_ENTITY";

    public YearExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "year";
    }
}
