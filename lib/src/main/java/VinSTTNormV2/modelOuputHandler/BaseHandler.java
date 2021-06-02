package VinSTTNormV2.modelOuputHandler;

import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import VinSTTNormV2.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BaseHandler {
    List<BaseNormalizer> normalizers = new ArrayList<>();

    public String normAll(String spanText){
        boolean normed = false;
        for(BaseNormalizer normalizer: normalizers){
            SpanObject[] spans = new SpanObject[]{
                    new SpanObject(0, spanText.length()-1, "", spanText.toLowerCase(Locale.ROOT))
            };
            normalizer.doAllNorm(spans);
            for(SpanObject span : spans){
                System.out.println(span.toString());
                if (span.text != span.replacement) normed = true;
            }
            if (normed){
                spanText = Utilities.replaceString(spanText, spans);
                break;
            }

        }
        return spanText;
    }
}
