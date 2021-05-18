package VinSTTNorm.asrnormalizer.abstractentity;

import VinSTTNorm.asrnormalizer.BaseEntity;
import VinSTTNorm.asrnormalizer.entityobject.EntityObject;
import VinSTTNorm.asrnormalizer.utilities.StringUtilities;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

abstract public class MapBaseEntity extends BaseEntity {
    protected final String TAG = "map_base_entity";

    protected int getMinWindowSize() {
        return 2;
    }

    protected int getMaxWindowSize() {
        return 4;
    }

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

    public MapBaseEntity(JSONObject config) {
        super(config);
    }

    public Map<String, String> getEntityMap() {
        Map<String, String> entityMap;
        if (this.cacheEntityMap != null) {
            entityMap = this.cacheEntityMap;
        } else {
            entityMap = this.loadEntityMap();
        }
        return entityMap;
    }

    public String[] splitText(String text) {
        return text.split(" ");
    }

    /**
     * Get a list of entities
     * @param text
     * @return
     */
    @Override
    public EntityObject[] getEntities(String text) {
        ArrayList<EntityObject> result = new ArrayList<>();

        Map<String, String> entityMap = this.getEntityMap();

        String[] tokens = this.splitText(text);

        int startIdx = 0;
        int stringStartIdx = 0;
        while (startIdx <= tokens.length - this.getMinWindowSize()) {
            for (int wSize = this.getMaxWindowSize() ; wSize >= this.getMinWindowSize()  ; wSize--) {
                int endIdx = startIdx + wSize - 1;
                if (endIdx >= tokens.length) {
                    continue;
                }

                String[] candidate = Arrays.copyOfRange(tokens, startIdx, endIdx+1);
                String candidateString = StringUtilities.join(candidate, " ");

                if (entityMap.containsKey(candidateString) || entityMap.containsKey(candidateString.toLowerCase(Locale.ROOT))) {
                    int stringEndIdx = stringStartIdx + candidateString.length() - 1;

                    result.add(new EntityObject(stringStartIdx, stringEndIdx, this.getType(), candidateString, this.normEntity(candidateString)));

                    startIdx = endIdx;
                    stringStartIdx = stringEndIdx - candidate[candidate.length-1].length() + 1;

                    break;
                }
            }

            stringStartIdx += tokens[startIdx].length() + 1;
            startIdx++;
        }

        EntityObject[] resultStringArray = new EntityObject[result.size()];
        result.toArray(resultStringArray);

        return resultStringArray;
    }

    @Override
    public String normEntity(String spokenFormEntityString) {
        return this.getEntityMap().get(spokenFormEntityString.toLowerCase(Locale.ROOT));
    }
}
