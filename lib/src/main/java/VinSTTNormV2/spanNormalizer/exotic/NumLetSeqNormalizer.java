package VinSTTNormV2.spanNormalizer.exotic;


import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import org.json.JSONObject;

import java.util.Locale;

public class NumLetSeqNormalizer extends BaseNormalizer {
    private final String TAG = "numletseq_normalizer";

    public NumLetSeqNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public String doNorm(String spokenFormEntityString) {return spokenFormEntityString.toUpperCase(Locale.ROOT).replace(" ", "");
    }

}
