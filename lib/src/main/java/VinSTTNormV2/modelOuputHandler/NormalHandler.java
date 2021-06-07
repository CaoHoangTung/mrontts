package VinSTTNormV2.modelOuputHandler;

import VinSTTNormV2.config.OnlineConfig;
import org.json.JSONObject;

public class NormalHandler extends BaseHandler{
    public NormalHandler(){
        super();
    }
    @Override
    public String normAll(String spanText){
        return spanText;
    }
}
