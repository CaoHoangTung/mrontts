package VinSTTNormV2.online;

import VinSTTNormV2.config.OnlineConfig;
import VinSTTNormV2.taggers.BaseTagger;
import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.verbalizers.BaseVerbalizer;
import VinSTTNormV2.utilities.Utilities;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BaseOutputNormalizer {

    class TaggerAndVerbalizer {
        public BaseTagger extractor;
        public BaseVerbalizer normalizer;

        public TaggerAndVerbalizer(BaseTagger extractor, BaseVerbalizer normalizer){
            this.extractor = extractor;
            this.normalizer = normalizer;
        }
    }

    public TaggerAndVerbalizer[] terms;

    List<BaseVerbalizer> normalizers = new ArrayList<>();
    JSONObject config;
    public BaseOutputNormalizer(){
        OnlineConfig onlineConfig = new OnlineConfig();
        config = onlineConfig.getConfig();
    }
    public String normAll(String spanText){
        boolean normed = false;
        for(BaseVerbalizer normalizer: normalizers){
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
