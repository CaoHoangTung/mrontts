package VinSTTNormV2.taggers.number;

import org.json.JSONObject;

public class FSTMillionNumberTagger extends FSTNumberTagger {
    public FSTMillionNumberTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getNumberType(){
        return "million_digit";
    }
}
