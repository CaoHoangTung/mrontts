package VinSTTNorm.asrnormalizer.propername;

import VinSTTNorm.asrnormalizer.abstractentity.NameMapEntity;
import org.json.JSONObject;

public class CommuneMapEntity extends NameMapEntity {

    public CommuneMapEntity(JSONObject config) {
        super(config);
    }

    @Override
    public String getType() {
        return "commune";
    }
}
