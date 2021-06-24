package VinSTTNormV2.taggers.exotic;

import VinSTTNormV2.taggers.NameMapTagger;
import org.json.JSONObject;

public class AbbreviationTagger extends NameMapTagger {

    private static String TAG = "LEXICON_ENTITY";

    public AbbreviationTagger(JSONObject config){
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
