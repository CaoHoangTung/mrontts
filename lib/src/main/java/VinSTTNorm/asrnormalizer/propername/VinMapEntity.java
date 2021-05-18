package VinSTTNorm.asrnormalizer.propername;

import VinSTTNorm.asrnormalizer.abstractentity.NameMapEntity;
import org.json.JSONObject;

public class VinMapEntity extends NameMapEntity {

    public VinMapEntity(JSONObject config) {
        super(config);
    }

    @Override
    protected int getMinWindowSize() {
        return 1;
    }

    @Override
    public String getType() {
        return "vin";
    }
}
