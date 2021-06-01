package VinSTTNormV2.spanExtractor.number.time;

import VinSTTNormV2.spanExtractor.SimpleRegexExtractor;
import org.json.JSONObject;

public class TimeExtractor extends SimpleRegexExtractor {
    private static String TAG = "TIME";

    public TimeExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "time";
    }

}
