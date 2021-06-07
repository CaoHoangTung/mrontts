package VinSTTNormV2.spanExtractor.exotic;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import org.json.JSONObject;

public class SegmentExtractor extends ReplaceRegexExtractor {
    public final String TAG = "segment_extractor";

    public SegmentExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "segment";
    }
}
