package VinSTTNormV2.taggers;

import org.json.JSONObject;

abstract public class BaseTagger {
    protected JSONObject globalConfig;

    public JSONObject getGlobalConfig() {
        return globalConfig;
    }

    /**
     * Get the type string for the entity
     * @return
     */
    abstract public String getType();




    public BaseTagger(JSONObject config) {
        this.globalConfig = config;
    }

    /**
     * Get a list of entities
     * @param text
     * @return
     */
    abstract public SpanObject[] getSpans(String text);

    /**
     * Check if the span string should be normed or not
     * @param spokenFormEntity
     * @param contextLeft
     * @param contextRight
     * @return
     */
    public boolean isException(String spokenFormEntity, String[] contextLeft, String[] contextRight) {
        return false;
    }

}
