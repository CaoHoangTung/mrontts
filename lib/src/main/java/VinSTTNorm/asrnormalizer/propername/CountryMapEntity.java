package VinSTTNorm.asrnormalizer.propername;

import VinSTTNorm.asrnormalizer.abstractentity.NameMapEntity;
import org.json.JSONObject;

public class CountryMapEntity extends NameMapEntity {

    public CountryMapEntity(JSONObject config) {
        super(config);
    }

    @Override
    protected int getMinWindowSize() {
        return 1;
    }

    @Override
    protected int getMaxWindowSize() {
        return 8;
    }

    @Override
    public String getType() {
        return "country";
    }
}
