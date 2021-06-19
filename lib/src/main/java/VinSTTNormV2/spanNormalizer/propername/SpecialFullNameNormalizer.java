package VinSTTNormV2.spanNormalizer.propername;

import VinSTTNormV2.spanNormalizer.NameMapNormalizer;
import org.json.JSONObject;

public class SpecialFullNameNormalizer  extends NameMapNormalizer {

    public final String TAG = "app_normalizer";

    public SpecialFullNameNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "special_full_name";
    }
}
