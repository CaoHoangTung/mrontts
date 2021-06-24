package VinSTTNormV2.taggers.number.time;

import VinSTTNormV2.taggers.SimpleRegexTagger;
import org.json.JSONObject;

public class TimeTagger extends SimpleRegexTagger {
    private static String TAG = "TIME";

    public TimeTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "time";
    }

}
