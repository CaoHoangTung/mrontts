package VinSTTNormV2.spanNormalizer;

import org.json.JSONObject;

import java.util.Locale;
import java.util.Map;

abstract public class MapNormalizer extends BaseNormalizer{
    protected final String TAG = "map_normalizer";
    Map<String, String> cacheEntityMap;

    /**
     * Get mapping for entity strings
     * Example: {
     *     "hoàng thành thăng long": "Hoàng Thành Thăng Long",
     *     "hà nội": "Hà Nội"
     * }
     * @return
     */
    abstract public Map<String,String> loadEntityMap();

    public MapNormalizer(JSONObject config){
        super(config);
    }

    public Map<String, String> getEntityMap(){
        Map<String, String> entityMap;
        if (this.cacheEntityMap != null) {
            entityMap = this.cacheEntityMap;
        } else {
            entityMap = this.loadEntityMap();
        }
        return entityMap;
    }

    @Override
    public String doNorm(String spokenFormEntityString){
        return this.getEntityMap().get(spokenFormEntityString.toLowerCase(Locale.ROOT));
    }
}
