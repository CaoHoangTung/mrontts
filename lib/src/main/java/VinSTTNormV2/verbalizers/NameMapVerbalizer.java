package VinSTTNormV2.verbalizers;

import VinSTTNormV2.utilities.ConfigUtilities;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

abstract public class NameMapVerbalizer extends MapVerbalizer {
    public static String TAG = "name_map_normalizer";

    public NameMapVerbalizer(JSONObject config){
        super(config);
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
