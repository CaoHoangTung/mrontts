package VinSTTNormV2.taggers.number;

import org.json.JSONObject;

public class FSTBillionNumberTagger extends FSTNumberTagger {
    public FSTBillionNumberTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getNumberType(){
        return "billion_digit";
    }
}
