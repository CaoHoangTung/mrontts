package VinSTTNormV2.spanExtractor.number.calculations;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import org.json.JSONObject;

public class SimpleCalculationExtractor extends ReplaceRegexExtractor {
    private static String TAG = "simple_calculation_extractor";

    public SimpleCalculationExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "simple_calculation";
    }
}
