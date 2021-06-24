package VinSTTNormV2.taggers.number.date;

import VinSTTNormV2.taggers.number.CommonNumberTagger;
import org.json.JSONObject;

public class MonthYearCountTagger extends CommonNumberTagger {
    private static String TAG = "month_year_count_entity";

    public MonthYearCountTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "month_year_count";
    }
}
