package VinSTTNorm.asrnormalizer.propername;

import VinSTTNorm.asrnormalizer.abstractentity.NameMapEntity;
import org.json.JSONObject;

public class AppNameMapEntity extends NameMapEntity {

    public AppNameMapEntity(JSONObject config) {
        super(config);
    }

    @Override
    public String getType() {
        return "app_name";
    }

    @Override
    protected int getMinWindowSize() {
        return 1;
    }

    @Override
    protected int getMaxWindowSize() {
        return 10;
    }
}
