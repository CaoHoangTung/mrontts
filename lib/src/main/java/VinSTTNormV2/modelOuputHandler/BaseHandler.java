package VinSTTNormV2.modelOuputHandler;

import VinSTTNormV2.config.OnlineConfig;
import VinSTTNormV2.spanExtractor.BaseExtractor;
import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import VinSTTNormV2.utilities.Utilities;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BaseHandler {

    class ExtractorAndNorm{
        public BaseExtractor extractor;
        public BaseNormalizer normalizer;

        public ExtractorAndNorm(BaseExtractor extractor, BaseNormalizer normalizer){
            this.extractor = extractor;
            this.normalizer = normalizer;
        }
    }

    public ExtractorAndNorm[] terms;

    List<BaseNormalizer> normalizers = new ArrayList<>();
    JSONObject config;
    public BaseHandler(){
        OnlineConfig onlineConfig = new OnlineConfig();
        config = onlineConfig.getConfig();
    }
    public String normAll(String spanText){
        boolean normed = false;
        for(BaseNormalizer normalizer: normalizers){
            SpanObject[] spans = new SpanObject[]{
                    new SpanObject(0, spanText.length()-1, "", spanText)
            };
            normalizer.doAllNorm(spans);
            for(SpanObject span : spans){
                System.out.println(span.toString());
                if (!span.text.equals(span.replacement)) normed = true;
            }
            if (normed){
                spanText = Utilities.replaceString(spanText, spans);
                break;
            }

        }
        return spanText;
    }
}
