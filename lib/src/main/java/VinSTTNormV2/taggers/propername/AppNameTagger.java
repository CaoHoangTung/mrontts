package VinSTTNormV2.taggers.propername;

import VinSTTNormV2.taggers.NameMapTagger;
import org.json.JSONObject;

public class AppNameTagger extends NameMapTagger {

    public AppNameTagger(JSONObject config){
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
