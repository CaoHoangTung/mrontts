package VinSTTNormV2.spanExtractor.propername;

import VinSTTNormV2.spanExtractor.BaseExtractor;
import VinSTTNormV2.utilities.ConfigUtilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

abstract public class BinarySetBaseExtractor extends BaseExtractor {
    protected final String TAG = "set_base_extractor";


    protected int getMinWindowSize(){ return 1; }
    protected int getMaxWindowSize(){ return 0; }

    protected Set<Integer> binaryWordSegmentationSet;

    public BinarySetBaseExtractor(JSONObject config){
        super(config);
        this.init();
    }

    public String[] splitText(String text) {
        return text.split(" ");
    }


    private void init(){
        try {
            String[] keys = {"word_segmentation"};
            JSONObject wordSegmentationConfig = ConfigUtilities.getConfigItem(this.getGlobalConfig(), keys);
            JSONArray wordSegmentationArray = wordSegmentationConfig.getJSONArray("word_list");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

}
