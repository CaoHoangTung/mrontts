package VinSTTNormV2.spanNormalizer.number;

import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import org.json.JSONObject;

public class FSTOneTwoThreeNumberNormalizer extends FSTNumberNormalizer {
    public FSTOneTwoThreeNumberNormalizer(JSONObject config) {
        super(config);
    }

    @Override
    public void doAllNorm(SpanObject[] spans) {
        return;
    }
}
