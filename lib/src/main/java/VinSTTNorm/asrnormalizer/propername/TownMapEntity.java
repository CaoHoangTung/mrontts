package VinSTTNorm.asrnormalizer.propername;

import VinSTTNorm.asrnormalizer.abstractentity.NameMapEntity;
import org.json.JSONObject;

public class TownMapEntity extends NameMapEntity {

    public TownMapEntity(JSONObject config) {
        super(config);
    }

    @Override
    public String getType() {
        return "town";
    }
}
