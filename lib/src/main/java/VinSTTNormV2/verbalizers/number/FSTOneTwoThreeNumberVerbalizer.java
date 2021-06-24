package VinSTTNormV2.verbalizers.number;

import VinSTTNormV2.taggers.SpanObject;
import org.json.JSONObject;

public class FSTOneTwoThreeNumberVerbalizer extends FSTNumberVerbalizer {
    public FSTOneTwoThreeNumberVerbalizer(JSONObject config) {
        super(config);
    }

    @Override
    public void doAllNorm(SpanObject[] spans) {
        return;
    }
}
