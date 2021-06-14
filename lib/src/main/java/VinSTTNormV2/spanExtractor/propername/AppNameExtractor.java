package VinSTTNormV2.spanExtractor.propername;

import VinSTTNormV2.spanExtractor.NameMapExtractor;
import org.json.JSONObject;

public class AppNameExtractor extends NameMapExtractor {

    public AppNameExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "app_name";
    }

    @Override
    protected int getMaxWindowSize(){
        return 10;
    }

    @Override
    protected int getMinWindowSize() {
        return 1;
    }
}
