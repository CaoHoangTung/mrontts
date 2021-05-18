package VinSTTNorm.asrnormalizer.stringreplacer;


import VinSTTNorm.asrnormalizer.abstractentity.ReplaceRegexEntity;
import org.json.JSONObject;

public class SqrtCalculationRegexEntity extends ReplaceRegexEntity {
    private static String TAG = "SQRT_CALCULATION_ENTITY";

    public SqrtCalculationRegexEntity(JSONObject config) {
        super(config);
    }
    
    @Override
    public String getType() {
        return "sqrt_calculation";
    }
}
