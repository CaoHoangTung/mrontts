package VinSTTNorm.asrnormalizer.stringreplacer;


import VinSTTNorm.asrnormalizer.abstractentity.ReplaceRegexEntity;
import org.json.JSONObject;

public class UnitRegexEntity extends ReplaceRegexEntity {
    private static String TAG = "UNIT_ENTITY";

    public UnitRegexEntity(JSONObject config) {
        super(config);
    }
    
    @Override
    public String getType() {
        return "unit";
    }
}
