package VinSTTNormV2.taggers.exotic;

import VinSTTNormV2.taggers.SimpleRegexTagger;
import org.json.JSONObject;

public class NumLetSeqTagger extends SimpleRegexTagger {
    private final String TAG = "num_let_seg_extractor";

    public NumLetSeqTagger(JSONObject config){
        super(config);
    }

    @Override
    public String getType(){
        return "written_serial_number_character";
    }

    @Override
    public boolean isException(String spokenFormEntity, String[] contextLeft, String[] contextRight) {
        return false;
    }
}
