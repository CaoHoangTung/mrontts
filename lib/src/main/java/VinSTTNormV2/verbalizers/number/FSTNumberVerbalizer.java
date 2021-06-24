package VinSTTNormV2.verbalizers.number;

import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.verbalizers.BaseVerbalizer;
import org.json.JSONObject;

abstract public class FSTNumberVerbalizer extends BaseVerbalizer {
    public FSTNumberVerbalizer(JSONObject config) {
        super(config);
    }

    /**
     * Smooth the written form text obtained from norm function
     * Ex: 20 19 => 2019 (year)
     *
     * @param writtenFormText
     * @return
     */
    public String smoothWrittenForm(String writtenFormText) {
        String joinText = writtenFormText.replaceAll(" ", "");
        if (joinText.length() == 4 && (joinText.startsWith("20") || joinText.startsWith("1"))) {
            writtenFormText = joinText;
        }
        return writtenFormText;
    }

    @Override
    public String doNorm(String spokenFormEntityString) {
        return this.smoothWrittenForm(spokenFormEntityString);
    }

    @Override
    public void doAllNorm(SpanObject[] spans) {
        return;
    }
}
