package VinSTTNormV2.verbalizers.number.calculations;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import VinSTTNormV2.taggers.number.calculations.SqrtCalculationTagger;
import VinSTTNormV2.verbalizers.ReplaceRegexVerbalizer;
import org.json.JSONObject;

public class SqrtCalculationVerbalizer extends ReplaceRegexVerbalizer {
    private static String TAG = "sqrt_calculation_normalizer";

    public SqrtCalculationVerbalizer(JSONObject config){
        super(config);
    }

    @Override
    public ReplaceRegexTagger getSampleExtractor(JSONObject config) {
        return new SqrtCalculationTagger(config);
    }

    @Override
    public String getType(){
        return "sqrt_calculation";
    }
}
