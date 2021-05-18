package VinSTTNorm.asrnormalizer.personname;

import VinSTTNorm.asrnormalizer.abstractentity.NameMapEntity;
import org.json.JSONObject;

public class SpecialFullNameEntity extends NameMapEntity {
    @Override
    protected int getMinWindowSize() {
        return 1;
    }

    public SpecialFullNameEntity(JSONObject config) {
        super(config);
    }

    @Override
    public String getType() {
        return "special_full_name";
    }
}
