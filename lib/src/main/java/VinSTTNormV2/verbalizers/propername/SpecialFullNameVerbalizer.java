package VinSTTNormV2.verbalizers.propername;

import VinSTTNormV2.verbalizers.NameMapVerbalizer;
import org.json.JSONObject;

public class SpecialFullNameVerbalizer extends NameMapVerbalizer {

    public final String TAG = "app_normalizer";

    public SpecialFullNameVerbalizer(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "special_full_name";
    }
}
