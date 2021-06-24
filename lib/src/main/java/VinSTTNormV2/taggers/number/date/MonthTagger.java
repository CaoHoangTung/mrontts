package VinSTTNormV2.taggers.number.date;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import org.json.JSONObject;

public class MonthTagger extends ReplaceRegexTagger {
    private static String TAG = "MONTH_ENTITY";

    public MonthTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "month";
    }
}
