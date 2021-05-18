package VinSTTNorm.asrnormalizer.config.normalizerconfig;

import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import VinSTTNorm.asrnormalizer.utilities.binaryencoder.BinaryDictionary;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

abstract public class BaseNormalizerConfig extends NormalizerConfig {
    private final String TAG = "BASE_NORMALIER_CONFIG";

    public BaseNormalizerConfig() {
        this.config = new JSONObject();

        try {
            HashMap<String, Set<Integer>> binaryWordSegmentationDict = new HashMap<>();
            binaryWordSegmentationDict.put("word_list", BinaryDictionary.getWords("cfg/entitycfg/global/word_segmentation.bin"));
            this.config.put("word_segmentation", new JSONObject(binaryWordSegmentationDict));

            this.config.put("not_norm_single_token", ConfigUtilities.getConfigFromFile("cfg/entitycfg/global/not_norm_single_token.json"));
            this.config.put("single_number_token", ConfigUtilities.getConfigFromFile("cfg/entitycfg/global/single_number_token.json"));
            this.config.put("positive_integer_pattern", ConfigUtilities.getConfigFromFile("cfg/entitycfg/global/positive_integer_pattern.json"));
            this.config.put("simple_calculation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/global/simple_calculation.json"));
//            this.config.put("word_segmentation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/global/word_segmentation.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void overwriteConfig(String path) {
        JSONObject overwriteConfig = ConfigUtilities.getConfigFromFile(path);

        Iterator<String> keys = overwriteConfig.keys();

        while (keys.hasNext()) {
            String key = keys.next();

            try {
                JSONObject item = overwriteConfig.getJSONObject(key);
                this.config.put(key, item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
