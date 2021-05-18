package VinSTTNorm.asrnormalizer.stringreplacer;


import VinSTTNorm.asrnormalizer.abstractentity.ReplaceRegexEntity;
import org.json.JSONObject;

public class NumberPunctuationRegexEntity extends ReplaceRegexEntity {
    private static String TAG = "NUMBER_PUNCTUATION_ENTITY";

    public NumberPunctuationRegexEntity(JSONObject config) {
        super(config);
    }
    
    @Override
    public String getType() {
        return "number_punctuation";
    }
}
