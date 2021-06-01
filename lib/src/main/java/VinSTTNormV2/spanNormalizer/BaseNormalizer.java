package VinSTTNormV2.spanNormalizer;

import VinSTTNormV2.spanExtractor.SpanObject;
import org.json.JSONObject;

abstract public class BaseNormalizer {
    protected String type;
    protected JSONObject globalConfig;

    public JSONObject getGlobalConfig() {
        return globalConfig;
    }

    public BaseNormalizer(JSONObject config) {
        this.globalConfig = config;
    }

    /**
     * norm a single entity text
     * @param spokenFormEntityString
     * @return
     */
    abstract public String doNorm(String spokenFormEntityString);

    public String getType(){
        return "";
    }

    public void doAllNorm(SpanObject[] spans){
        for (int i = 0; i < spans.length; i++){
            spans[i].replacement = doNorm(spans[i].text);
        }
//        return spans;
    }
}
