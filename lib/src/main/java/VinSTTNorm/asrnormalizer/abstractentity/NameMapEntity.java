package VinSTTNorm.asrnormalizer.abstractentity;

import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

abstract public class NameMapEntity extends MapBaseEntity {
    private Map<String, String> cacheEntityMap;

    public NameMapEntity(JSONObject config) {
        super(config);
    }

    @Override
    public boolean isException(String spokenFormEntity, String[] contextLeft, String[] contextRight) {
        return false;
    }

    @Override
    public Map<String, String> loadEntityMap() {
        Map<String, String> entityMap = new HashMap<>();

        try {
            if (this.cacheEntityMap != null) {
                entityMap = this.cacheEntityMap;
            } else {
                String[] keys = {this.getType(), "dict"};
                JSONObject entityMapJSON = ConfigUtilities.getConfigItem(this.getGlobalConfig(), keys);

                entityMap = ConfigUtilities.getMapFromJSONObject(entityMapJSON);
                this.cacheEntityMap = entityMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(TAG +  "Error load entity map for " + this.getType());
        }
        return entityMap;
    }
}
