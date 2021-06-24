package VinSTTNormV2.taggers.propername;

import VinSTTNormV2.taggers.BaseTagger;
import VinSTTNormV2.utilities.ConfigUtilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

abstract public class BinarySetBaseTagger extends BaseTagger {
    protected final String TAG = "set_base_extractor";


    protected int getMinWindowSize(){ return 1; }
    protected int getMaxWindowSize(){ return 0; }

    protected Set<Integer> binaryWordSegmentationSet;

    public BinarySetBaseTagger(JSONObject config){
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
