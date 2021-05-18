package VinSTTNorm.asrnormalizer.propername;

import VinSTTNorm.asrnormalizer.abstractentity.NameMapEntity;
import org.json.JSONObject;

public class WebsiteNameMapEntity extends NameMapEntity {

    public WebsiteNameMapEntity(JSONObject config) {
        super(config);
    }

    @Override
    public String getType() {
        return "website_name";
    }

    @Override
    protected int getMaxWindowSize() {
        return 10;
    }
}
