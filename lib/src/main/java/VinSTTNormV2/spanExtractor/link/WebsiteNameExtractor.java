package VinSTTNormV2.spanExtractor.link;

import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import org.json.JSONObject;

public class WebsiteNameExtractor extends ReplaceRegexExtractor {
    private static String TAG = "website_name_extractor";

    public WebsiteNameExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "website_name";
    }

    @Override
    public boolean isCaseSensitive(){
        return false;
    }
}
