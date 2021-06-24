package VinSTTNormV2.taggers.number.calculations;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import org.json.JSONObject;

public class SqrtCalculationTagger extends ReplaceRegexTagger {
    private static String TAG = "sqrt_calculation_entity";

    public SqrtCalculationTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getType() {
        return "sqrt_calculation";
    }
}
