package VinSTTNormV2.spanExtractor.number.date;

import VinSTTNormV2.spanExtractor.number.CommonNumberExtractor;
import org.json.JSONObject;

public class MonthYearCountExtractor extends CommonNumberExtractor {
    private static String TAG = "month_year_count_entity";

    public MonthYearCountExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "month_year_count";
    }
}
