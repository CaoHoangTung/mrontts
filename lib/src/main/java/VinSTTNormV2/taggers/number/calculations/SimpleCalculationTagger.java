package VinSTTNormV2.taggers.number.calculations;

import VinSTTNormV2.taggers.ReplaceRegexTagger;
import org.json.JSONObject;

public class SimpleCalculationTagger extends ReplaceRegexTagger {
    private static String TAG = "simple_calculation_extractor";

    public SimpleCalculationTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "simple_calculation";
    }
}
