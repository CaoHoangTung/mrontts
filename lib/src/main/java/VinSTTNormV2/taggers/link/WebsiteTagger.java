package VinSTTNormV2.taggers.link;

import VinSTTNormV2.taggers.NameMapTagger;
import org.json.JSONObject;

public class WebsiteTagger extends NameMapTagger {
    public WebsiteTagger(JSONObject config){
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
