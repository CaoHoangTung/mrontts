package VinSTTNormV2.spanExtractor.link;

import VinSTTNormV2.spanExtractor.NameMapExtractor;
import org.json.JSONObject;

public class WebsiteExtractor extends NameMapExtractor {
    public WebsiteExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "website_name";
    }

    @Override
    protected int getMaxWindowSize(){
        return 10;
    }
}
