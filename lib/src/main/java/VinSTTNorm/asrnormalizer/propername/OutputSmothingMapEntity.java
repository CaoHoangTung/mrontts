package VinSTTNorm.asrnormalizer.propername;

import VinSTTNorm.asrnormalizer.abstractentity.NameMapEntity;
import org.json.JSONObject;

public class OutputSmothingMapEntity extends NameMapEntity {

    public OutputSmothingMapEntity(JSONObject config) {
        super(config);
    }

    @Override
    protected int getMinWindowSize() {
        return 2;
    }

    @Override
    public String getType() {
        return "output_smoothing";
    }
}
