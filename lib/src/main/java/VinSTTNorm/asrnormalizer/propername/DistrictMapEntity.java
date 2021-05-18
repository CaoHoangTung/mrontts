package VinSTTNorm.asrnormalizer.propername;

import VinSTTNorm.asrnormalizer.abstractentity.NameMapEntity;
import org.json.JSONObject;

public class DistrictMapEntity extends NameMapEntity {

    public DistrictMapEntity(JSONObject config) {
        super(config);
    }

    @Override
    public String getType() {
        return "district";
    }
}
