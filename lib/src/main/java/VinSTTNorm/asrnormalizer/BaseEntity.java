package VinSTTNorm.asrnormalizer;

import VinSTTNorm.asrnormalizer.entityobject.EntityObject;
import org.json.JSONObject;

abstract public class BaseEntity {
    protected JSONObject globalConfig;

    public JSONObject getGlobalConfig() {
        return globalConfig;
    }

    /**
     * Get the type string for the entity
     * @return
     */
    abstract public String getType();

    /**
     * norm a single entity text
     * @param spokenFormEntityString
     * @return
     */
    abstract public String normEntity(String spokenFormEntityString);


    public BaseEntity(JSONObject config) {
        this.globalConfig = config;
    }

    /**
     * Get a list of entities
     * @param text
     * @return
     */
    abstract public EntityObject[] getEntities(String text);

    /**
     * Check if the entity string should be normed or not
     * @param spokenFormEntity
     * @param contextLeft
     * @param contextRight
     * @return
     */
    public boolean isException(String spokenFormEntity, String[] contextLeft, String[] contextRight) {
        return false;
    }
}
