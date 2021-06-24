package VinSTTNormV2.taggers.propername;

import VinSTTNormV2.taggers.NameMapTagger;
import org.json.JSONObject;

public class SpecialFullNameTagger extends NameMapTagger {

    @Override
    protected int getMinWindowSize() {
        return 1;
    }

    public SpecialFullNameTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getType() {
        return "special_full_name";
    }
}
