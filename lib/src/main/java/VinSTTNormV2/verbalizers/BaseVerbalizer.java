package VinSTTNormV2.verbalizers;

import VinSTTNormV2.taggers.SpanObject;
import org.json.JSONObject;

abstract public class BaseVerbalizer {
    protected String type;
    protected JSONObject globalConfig;

    public JSONObject getGlobalConfig() {
        return globalConfig;
    }

    public BaseVerbalizer(JSONObject config) {
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
            String replacement = doNorm(spans[i].text);
            if (replacement == null || (replacement.isEmpty() && !spans[i].text.equals(" "))){
                spans[i].replacement = spans[i].text;
            } else {
                spans[i].replacement = replacement;
            }

        }
//        return spans;
    }
}
