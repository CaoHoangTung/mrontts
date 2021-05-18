package VinSTTNorm.asrnormalizer.stringreplacer;


import VinSTTNorm.asrnormalizer.abstractentity.ReplaceRegexEntity;
import org.json.JSONObject;

public class SegmentRegexEntity extends ReplaceRegexEntity {
    private static String TAG = "SEGMENT_ENTITY";

    public SegmentRegexEntity(JSONObject config) {
        super(config);
    }
    
    @Override
    public String getType() {
        return "segment";
    }
}
