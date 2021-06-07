package VinSTTNormV2.spanExtractor.exotic;

import VinSTTNormV2.spanExtractor.SimpleRegexExtractor;
import org.json.JSONObject;

public class NumLetSeqExtractor extends SimpleRegexExtractor {
    private final String TAG = "num_let_seg_extractor";

    public NumLetSeqExtractor(JSONObject config){
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
