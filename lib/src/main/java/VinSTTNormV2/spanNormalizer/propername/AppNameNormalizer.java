package VinSTTNormV2.spanNormalizer.propername;

import VinSTTNormV2.spanNormalizer.NameMapNormalizer;
import org.json.JSONObject;

public class AppNameNormalizer extends NameMapNormalizer {
    public final String TAG = "app-normalizer";

    public AppNameNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "app_name";
    }
}
