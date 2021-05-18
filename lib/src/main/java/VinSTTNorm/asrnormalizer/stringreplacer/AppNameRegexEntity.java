package VinSTTNorm.asrnormalizer.stringreplacer;


import VinSTTNorm.asrnormalizer.abstractentity.ReplaceRegexEntity;
import org.json.JSONObject;

public class AppNameRegexEntity extends ReplaceRegexEntity {
    private static String TAG = "APP_NAME_ENTITY";

    public AppNameRegexEntity(JSONObject config) {
        super(config);
    }
    
    @Override
    public String getType() {
        return "app_name";
    }

    @Override
    public boolean isCaseSensitive() {
        return false;
    }
}
