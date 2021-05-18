package VinSTTNorm.asrnormalizer.propername;

import VinSTTNorm.asrnormalizer.abstractentity.NameMapEntity;
import org.json.JSONObject;

public class SportMapEntity extends NameMapEntity {

    public SportMapEntity(JSONObject config) {
        super(config);
    }

    @Override
    protected int getMinWindowSize() {
        return 1;
    }

    @Override
    public String getType() {
        return "sport";
    }
}
