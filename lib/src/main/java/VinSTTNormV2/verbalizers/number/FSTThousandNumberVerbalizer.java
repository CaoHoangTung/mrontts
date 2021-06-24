package VinSTTNormV2.verbalizers.number;

import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.verbalizers.BaseVerbalizer;
import org.json.JSONObject;

public class FSTThousandNumberVerbalizer extends BaseVerbalizer {
    public FSTThousandNumberVerbalizer(JSONObject config) {
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
