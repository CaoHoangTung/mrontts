package VinSTTNormV2.verbalizers.number.date;

import VinSTTNormV2.verbalizers.number.CommonNumberVerbalizer;
import org.json.JSONObject;

public class MonthYearCountVerbalizer extends CommonNumberVerbalizer {
    private static String TAG = "month_year_count_normalizer";

    public MonthYearCountVerbalizer(JSONObject config){
        super(config);
    }
}
