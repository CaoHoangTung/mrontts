package VinSTTNormV2.verbalizers.exotic;

import VinSTTNormV2.verbalizers.NameMapVerbalizer;
import org.json.JSONObject;

public class AbbreviationVerbalizer extends NameMapVerbalizer {
    public static String TAG = "abbreviation_normalizer";

    public AbbreviationVerbalizer(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "abbreviation";
    }
}
