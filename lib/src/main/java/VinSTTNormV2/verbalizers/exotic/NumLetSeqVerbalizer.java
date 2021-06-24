package VinSTTNormV2.verbalizers.exotic;


import VinSTTNormV2.verbalizers.BaseVerbalizer;
import org.json.JSONObject;

import java.util.Locale;

public class NumLetSeqVerbalizer extends BaseVerbalizer {
    private final String TAG = "numletseq_normalizer";

    public NumLetSeqVerbalizer(JSONObject config){
        super(config);
    }

    @Override
    public String doNorm(String spokenFormEntityString) {return spokenFormEntityString.toUpperCase(Locale.ROOT).replace(" ", "");
    }

}
