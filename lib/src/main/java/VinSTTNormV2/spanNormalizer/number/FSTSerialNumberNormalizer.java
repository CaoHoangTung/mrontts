package VinSTTNormV2.spanNormalizer.number;

import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import org.json.JSONObject;

public class FSTSerialNumberNormalizer extends BaseNormalizer {
    public FSTSerialNumberNormalizer(JSONObject config) {
        super(config);
    }
    @Override
    public String doNorm(String spokenFormEntityString) {
        return null;
    }

    @Override
    public void doAllNorm(SpanObject[] spans) {
        return;
    }
}
