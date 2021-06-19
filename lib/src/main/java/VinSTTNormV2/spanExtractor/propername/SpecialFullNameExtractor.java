package VinSTTNormV2.spanExtractor.propername;

import VinSTTNormV2.spanExtractor.NameMapExtractor;
import org.json.JSONObject;

public class SpecialFullNameExtractor extends NameMapExtractor {

    @Override
    protected int getMinWindowSize() {
        return 1;
    }

    public SpecialFullNameExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getType() {
        return "special_full_name";
    }
}
