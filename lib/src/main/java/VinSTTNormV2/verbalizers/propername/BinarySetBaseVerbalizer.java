package VinSTTNormV2.verbalizers.propername;

import VinSTTNormV2.verbalizers.BaseVerbalizer;
import VinSTTNormV2.utilities.StringUtilities;
import org.json.JSONObject;

abstract public class BinarySetBaseVerbalizer extends BaseVerbalizer {
    protected final String TAG  = "set_base_normalizer";

    public BinarySetBaseVerbalizer(JSONObject config){
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
