package VinSTTNormV2.spanNormalizer.exotic;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import VinSTTNormV2.spanExtractor.exotic.SegmentExtractor;
import VinSTTNormV2.spanNormalizer.ReplaceRegexNormalizer;
import org.json.JSONObject;

public class SegmentNormalizer extends ReplaceRegexNormalizer {
    public final String TAG = "segment_normalizer";

    public SegmentNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public ReplaceRegexExtractor getSampleExtractor(JSONObject config){
        return new SegmentExtractor(config);
    }
}
