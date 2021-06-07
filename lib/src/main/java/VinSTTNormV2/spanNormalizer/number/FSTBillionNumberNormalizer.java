package VinSTTNormV2.spanNormalizer.number;

import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import org.json.JSONObject;

public class FSTBillionNumberNormalizer extends BaseNormalizer {
    public FSTBillionNumberNormalizer(JSONObject config) {
        super(config);
    }

    @Override
    public String doNorm(String spokenFormEntityString) {
        return null;
    }

    @Override
    public void doAllNorm(SpanObject[] spans) {
        for (SpanObject span: spans){
            String replacement = span.replacement;
            if (replacement.substring(replacement.length()-9, replacement.length()).equals("000000000")){
                span.replacement = replacement.substring(0, replacement.length()-9) +  " tỉ";
            }
            if (replacement.substring(replacement.length()-6, replacement.length()).equals("000000")){
                span.replacement = replacement.substring(0, replacement.length()-9) + " tỉ " + replacement.substring(replacement.length()-9, replacement.length()-6);
            }
        }
        return;
    }
}
