package VinSTTNormV2.taggers.exotic;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import org.json.JSONObject;

public class SegmentTagger extends ReplaceRegexTagger {
    public final String TAG = "segment_extractor";

    public SegmentTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "segment";
    }
}
