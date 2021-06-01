package VinSTTNormV2.spanNormalizer.exotic;

import VinSTTNormV2.spanNormalizer.NameMapNormalizer;
import org.json.JSONObject;

public class AbbreviationNormalizer extends NameMapNormalizer {
    public static String TAG = "abbreviation_normalizer";

    public AbbreviationNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "abbreviation";
    }
}
