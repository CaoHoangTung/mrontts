package VinSTTNormV2.spanNormalizer.propername;

import VinSTTNormV2.spanNormalizer.NameMapNormalizer;
import org.json.JSONObject;

public class SameDictNameNormalizer extends NameMapNormalizer {
    public final String TAG = "same_dict_normalizer";

    public SameDictNameNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public String getType() {
        return "all_proper_name";
    }
}
