package VinSTTNorm.asrnormalizer.propername;

import VinSTTNorm.asrnormalizer.abstractentity.NameMapEntity;
import org.json.JSONObject;

public class WardMapEntity extends NameMapEntity {

    public WardMapEntity(JSONObject config) {
        super(config);
    }

    @Override
    public String getType() {
        return "ward";
    }
}
