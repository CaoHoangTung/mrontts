package VinSTTNormV2.spanNormalizer.number.calculations;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import VinSTTNormV2.spanExtractor.number.calculations.SimpleCalculationExtractor;
import VinSTTNormV2.spanNormalizer.ReplaceRegexNormalizer;
import org.json.JSONObject;

public class SimpleCalculationNormalizer extends ReplaceRegexNormalizer {
    private static String TAG = "simple_calculation_normalizer";

    public SimpleCalculationNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public ReplaceRegexExtractor getSampleExtractor(JSONObject config) {
        return new SimpleCalculationExtractor(config);
    }

    @Override
    public String getType() {
        return "simple_calculation";
    }

}
