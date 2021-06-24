package VinSTTNormV2.verbalizers.number;

import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.verbalizers.BaseVerbalizer;
import org.json.JSONObject;

public class FSTSerialNumberVerbalizer extends BaseVerbalizer {
    public FSTSerialNumberVerbalizer(JSONObject config) {
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
