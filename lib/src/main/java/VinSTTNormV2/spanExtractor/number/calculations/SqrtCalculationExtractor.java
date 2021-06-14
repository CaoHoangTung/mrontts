package VinSTTNormV2.spanExtractor.number.calculations;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import org.json.JSONObject;

public class SqrtCalculationExtractor extends ReplaceRegexExtractor {
    private static String TAG = "sqrt_calculation_entity";

    public SqrtCalculationExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType() {
        return "sqrt_calculation";
    }
}
