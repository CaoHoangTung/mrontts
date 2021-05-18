package VinSTTNorm.asrnormalizer.propername;

import VinSTTNorm.asrnormalizer.abstractentity.NameMapEntity;
import org.json.JSONObject;

public class OtherMapEntity extends NameMapEntity {

    public OtherMapEntity(JSONObject config) {
        super(config);
    }

    @Override
    protected int getMinWindowSize() {
        return 1;
    }

    @Override
    public String getType() {
        return "other";
    }
}
