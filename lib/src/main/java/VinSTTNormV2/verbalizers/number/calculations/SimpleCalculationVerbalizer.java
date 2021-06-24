package VinSTTNormV2.verbalizers.number.calculations;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import VinSTTNormV2.taggers.number.calculations.SimpleCalculationTagger;
import VinSTTNormV2.verbalizers.ReplaceRegexVerbalizer;
import org.json.JSONObject;

public class SimpleCalculationVerbalizer extends ReplaceRegexVerbalizer {
    private static String TAG = "simple_calculation_normalizer";

    public SimpleCalculationVerbalizer(JSONObject config){
        super(config);
    }

    @Override
    public ReplaceRegexTagger getSampleExtractor(JSONObject config) {
        return new SimpleCalculationTagger(config);
    }

    @Override
    public String getType() {
        return "simple_calculation";
    }

}
