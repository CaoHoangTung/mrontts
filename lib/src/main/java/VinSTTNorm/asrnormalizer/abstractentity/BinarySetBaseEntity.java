package VinSTTNorm.asrnormalizer.abstractentity;

import VinSTTNorm.asrnormalizer.BaseEntity;
import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import VinSTTNorm.asrnormalizer.utilities.StringUtilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

abstract public class BinarySetBaseEntity extends BaseEntity {
    protected final String TAG = "set_base_entity";

    protected int getMinWindowSize() {
        return 1;
    }
    protected int getMaxWindowSize() {
        return 8;
    }

    protected Set<Integer> binaryWordSegmentationSet;

    public BinarySetBaseEntity(JSONObject config) {
        super(config);
        this.init();
    }

    private void init() {
        try {
            String[] keys = {"word_segmentation"};
            JSONObject wordSegmentationConfig = ConfigUtilities.getConfigItem(this.getGlobalConfig(), keys);
            JSONArray wordSegmentationArray = wordSegmentationConfig.getJSONArray("word_list");
            this.binaryWordSegmentationSet = ConfigUtilities.getSetFromJSONArray(wordSegmentationArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String[] splitText(String text) {
        return text.split(" ");
    }

    @Override
    public String normEntity(String spokenFormEntityString) {
        String[] tokens = spokenFormEntityString.split(" ");
        for (int idx = 0 ; idx < tokens.length ; idx++) {
            tokens[idx] = Character.toUpperCase(tokens[idx].charAt(0)) + tokens[idx].substring(1);
        }
        return StringUtilities.join(tokens, " ");
    }

}
