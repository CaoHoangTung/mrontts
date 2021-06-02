package VinSTTNormV2.spanExtractor.number;

import org.json.JSONObject;

public class FSTThousandNumberExtractor extends FSTNumberExtractor{
    public FSTThousandNumberExtractor(JSONObject config){
        super(config);
    }

    @Override
    public String getNumberType(){
        return "thousand_digit";
    }
}
