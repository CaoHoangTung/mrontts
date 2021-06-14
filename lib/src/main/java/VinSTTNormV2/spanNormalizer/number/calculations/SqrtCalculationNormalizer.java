package VinSTTNormV2.spanNormalizer.number.calculations;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import VinSTTNormV2.spanExtractor.number.calculations.SqrtCalculationExtractor;
import VinSTTNormV2.spanNormalizer.ReplaceRegexNormalizer;
import org.json.JSONObject;

public class SqrtCalculationNormalizer extends ReplaceRegexNormalizer {
    private static String TAG = "sqrt_calculation_normalizer";

    public SqrtCalculationNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public ReplaceRegexExtractor getSampleExtractor(JSONObject config) {
        return new SqrtCalculationExtractor(config);
    }

    @Override
    public String getType(){
        return "sqrt_calculation";
    }
}
