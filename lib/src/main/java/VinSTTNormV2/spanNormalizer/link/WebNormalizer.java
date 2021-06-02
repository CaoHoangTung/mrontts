package VinSTTNormV2.spanNormalizer.link;

import VinSTTNormV2.spanNormalizer.NameMapNormalizer;
import org.json.JSONObject;

public class WebNormalizer extends NameMapNormalizer {
    public final String TAG = "web_normalizer";

    public WebNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "website_name";
    }
}
