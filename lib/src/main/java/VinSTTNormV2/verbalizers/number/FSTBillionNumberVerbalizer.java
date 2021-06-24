package VinSTTNormV2.verbalizers.number;

import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.verbalizers.BaseVerbalizer;
import org.json.JSONObject;

public class FSTBillionNumberVerbalizer extends BaseVerbalizer {
    public FSTBillionNumberVerbalizer(JSONObject config) {
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
//            System.out.println(replacement.substring(replacement.length()-9, replacement.length()));
            if (replacement.substring(replacement.length()-9, replacement.length()).equals("000000000")){
                span.replacement = replacement.substring(0, replacement.length()-9) +  " tỷ";
            }
            else if (replacement.substring(replacement.length()-8, replacement.length()).equals("00000000")){
                span.replacement = replacement.substring(0, replacement.length()-9) + " tỷ " + replacement.substring(replacement.length()-9, replacement.length()-8);
            }
            else if (replacement.substring(replacement.length()-6, replacement.length()).equals("000000")){
                span.replacement = replacement.substring(0, replacement.length()-9) + " tỷ " + replacement.substring(replacement.length()-9, replacement.length()-6);
            }
        }
        return;
    }
}
