package VinSTTNormV2.spanExtractor.number.date;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import org.json.JSONObject;

public class MonthExtractor extends ReplaceRegexExtractor {
    private static String TAG = "MONTH_ENTITY";

    public MonthExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "month";
    }
}
