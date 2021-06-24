package VinSTTNormV2.verbalizers.propername;

import VinSTTNormV2.verbalizers.NameMapVerbalizer;
import org.json.JSONObject;

public class SameDictNameVerbalizer extends NameMapVerbalizer {
    public final String TAG = "same_dict_normalizer";

    public SameDictNameVerbalizer(JSONObject config){
        super(config);
    }

    @Override
    public String getType() {
        return "all_proper_name";
    }
}
