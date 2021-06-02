package VinSTTNormV2.spanNormalizer.number.date;

import VinSTTNormV2.spanNormalizer.number.CommonNumberNormalizer;
import org.json.JSONObject;

public class MonthYearCountNormalizer extends CommonNumberNormalizer {
    private static String TAG = "month_year_count_normalizer";

    public MonthYearCountNormalizer(JSONObject config){
        super(config);
    }
}
