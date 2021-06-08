package VinSTTNormV2.spanNormalizer.propername;

import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import VinSTTNormV2.utilities.StringUtilities;
import org.json.JSONObject;

abstract public class BinarySetBaseNormalizer extends BaseNormalizer {
    protected final String TAG  = "set_base_normalizer";

    public BinarySetBaseNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public String doNorm(String spokenFormEntityString){
        String[] tokens = spokenFormEntityString.split(" ");
        for (int idx = 0 ; idx < tokens.length ; idx++ ){
            tokens[idx] = Character.toUpperCase(tokens[idx].charAt(0)) + tokens[idx].substring(1);
        }
        return StringUtilities.join(tokens, " ");
    }
}
