package VinSTTNormV2.spanExtractor.exotic;

import VinSTTNormV2.spanExtractor.NameMapExtractor;
import org.json.JSONObject;

public class AbbreviationExtractor extends NameMapExtractor {

    private static String TAG = "LEXICON_ENTITY";

    public AbbreviationExtractor(JSONObject config){
        super(config);
    }
    @Override
    protected int getMinWindowSize(){
        return 1;
    }

    @Override
    public String getType(){
        return "abbreviation";
    }
}
