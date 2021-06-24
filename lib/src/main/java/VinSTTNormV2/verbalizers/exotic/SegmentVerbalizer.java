package VinSTTNormV2.verbalizers.exotic;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import VinSTTNormV2.taggers.exotic.SegmentTagger;
import VinSTTNormV2.verbalizers.ReplaceRegexVerbalizer;
import org.json.JSONObject;

public class SegmentVerbalizer extends ReplaceRegexVerbalizer {
    public final String TAG = "segment_normalizer";

    public SegmentVerbalizer(JSONObject config){
        super(config);
    }

    @Override
    public ReplaceRegexTagger getSampleExtractor(JSONObject config){
        return new SegmentTagger(config);
    }
}
