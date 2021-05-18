package VinSTTNorm.asrnormalizer.stringreplacer;


import VinSTTNorm.asrnormalizer.abstractentity.ReplaceRegexEntity;
import org.json.JSONObject;

public class MonthRegexEntity extends ReplaceRegexEntity {
    private static String TAG = "MONTH_ENTITY";

    public MonthRegexEntity(JSONObject config) {
        super(config);
    }
    
    @Override
    public String getType() {
        return "month";
    }
}
