package VinSTTNormV2.spanNormalizer.number.date;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import VinSTTNormV2.spanExtractor.number.date.MonthExtractor;
import VinSTTNormV2.spanNormalizer.ReplaceRegexNormalizer;
import org.json.JSONObject;

public class MonthNormalizer extends ReplaceRegexNormalizer {

    public MonthNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public ReplaceRegexExtractor getSampleExtractor(JSONObject config){
        return new MonthExtractor(config);
    }

}
