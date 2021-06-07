package VinSTTNormV2.spanExtractor.number;

import org.json.JSONObject;

public class FSTMillionNumberExtractor extends FSTNumberExtractor{
    public FSTMillionNumberExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getNumberType(){
        return "million_digit";
    }
}
