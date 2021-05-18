package VinSTTNorm.asrnormalizer.stringreplacer;


import VinSTTNorm.asrnormalizer.abstractentity.ReplaceRegexEntity;
import org.json.JSONObject;

public class LexiconRegexEntity extends ReplaceRegexEntity {
    private static String TAG = "LEXICON_ENTITY";

    public LexiconRegexEntity(JSONObject config) {
        super(config);
    }
    
    @Override
    public String getType() {
        return "lexicon";
    }
}
