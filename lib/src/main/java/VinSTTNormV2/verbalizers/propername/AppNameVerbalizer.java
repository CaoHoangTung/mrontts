package VinSTTNormV2.verbalizers.propername;

import VinSTTNormV2.verbalizers.NameMapVerbalizer;
import org.json.JSONObject;

public class AppNameVerbalizer extends NameMapVerbalizer {
    public final String TAG = "app-normalizer";

    public AppNameVerbalizer(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "app_name";
    }
}
