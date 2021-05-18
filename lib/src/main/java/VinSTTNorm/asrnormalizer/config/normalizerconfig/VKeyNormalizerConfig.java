package VinSTTNorm.asrnormalizer.config.normalizerconfig;

import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import VinSTTNorm.asrnormalizer.utilities.binaryencoder.BinaryDictionary;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

public class VKeyNormalizerConfig extends BaseNormalizerConfig {
    public VKeyNormalizerConfig() {
        try {
            this.config.put("lexicon", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/lexicon.json"));
            this.config.put("website_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/website_name.json"));
            this.config.put("app_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/app_name.json"));

            HashMap<String, Set<Integer>> binaryProperNameDict = new HashMap<>();
            binaryProperNameDict.put("word_list", BinaryDictionary.getWords("cfg/entitycfg/vfast/all_proper_name.bin"));
            this.config.put("all_proper_name", new JSONObject(binaryProperNameDict));

//            this.config.put("all_proper_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/vfast/all_proper_name.json"));
            this.config.put("special_full_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/special_full_name.json"));
            this.config.put("person_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/person_name.json"));
            this.config.put("serial_number", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/serial_number.json"));
            this.config.put("month", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/month.json"));
            this.config.put("year", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/year.json"));
            this.config.put("month_year_count", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/month_year_count.json"));

            this.config.put("number", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number.json"));

            this.config.put("number_punctuation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number_punctuation.json"));
            this.config.put("time", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/time.json"));
            this.config.put("sqrt_calculation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/sqrt_calculation.json"));
            this.config.put("unit", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/unit.json"));
            this.config.put("match_result", ConfigUtilities.getConfigFromFile("cfg/entitycfg/vkey/unit.json"));

            this.config.put("character_lexicon", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/character_lexicon.json"));
            this.config.put("abbreviation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/abbreviation.json"));
            this.config.put("segment", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/segment.json"));
            this.config.put("written_serial_number_character", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/written_serial_number_character.json"));
            this.config.put("match_result", ConfigUtilities.getConfigFromFile("cfg/entitycfg/vkey/match_result.json"));

            this.config.put("sentence_punctuation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/vkey/sentence_punctuation.json"));

            this.config.put("output_smoothing", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/output_smoothing.json"));
        } catch (JSONException e) {
            System.out.println("Error loading config");
            e.printStackTrace();
        }
    }
}
