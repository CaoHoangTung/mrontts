package VinSTTNorm.asrnormalizer.stringreplacer;


import VinSTTNorm.asrnormalizer.abstractentity.ReplaceRegexEntity;
import org.json.JSONObject;

public class WebsiteNameRegexEntity extends ReplaceRegexEntity {
    private static String TAG = "WEBSITE_NAME_ENTITY";

    public WebsiteNameRegexEntity(JSONObject config) {
        super(config);
    }
    
    @Override
    public String getType() {
        return "website_name";
    }

    @Override
    public boolean isCaseSensitive() {
        return false;
    }
}
