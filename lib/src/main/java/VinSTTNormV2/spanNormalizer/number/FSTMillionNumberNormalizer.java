package VinSTTNormV2.spanNormalizer.number;

import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import org.json.JSONObject;

public class FSTMillionNumberNormalizer extends BaseNormalizer {
    public FSTMillionNumberNormalizer(JSONObject config) {
        super(config);
    }

    @Override
    public String doNorm(String spokenFormEntityString) {
        return null;
    }

    @Override
    public void doAllNorm(SpanObject[] spans) {
//        for (SpanObject span: spans){
//            String replacement = span.replacement;
//            if (replacement.substring(replacement.length()-6, replacement.length()).equals("000000")){
//                span.replacement = replacement.substring(0, replacement.length()-6) +  " triệu";
//            }
//            else if (replacement.substring(replacement.length()-5, replacement.length()).equals("00000")){
//                span.replacement = replacement.substring(0, replacement.length()-6) +  " triệu " + replacement.substring(replacement.length()-6, replacement.length()-5);
//            }
//        }
        return;
    }
}
