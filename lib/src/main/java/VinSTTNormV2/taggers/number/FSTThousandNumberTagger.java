package VinSTTNormV2.taggers.number;

import org.json.JSONObject;

public class FSTThousandNumberTagger extends FSTNumberTagger {
    public FSTThousandNumberTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getNumberType(){
        return "thousand_digit";
    }
}
