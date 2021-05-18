package VinSTTNorm.asrnormalizer.stringreplacer;


import VinSTTNorm.asrnormalizer.abstractentity.ReplaceRegexEntity;
import org.json.JSONObject;

public class SimpleCalculationRegexEntity extends ReplaceRegexEntity {
    private static String TAG = "SIMPLE_CALCULATION_ENTITY";

    public SimpleCalculationRegexEntity(JSONObject config) {
        super(config);
    }
    
    @Override
    public String getType() {
        return "simple_calculation";
    }
}
