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
            this.config.put("lexicon", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/text/lexicon.json"));
            this.config.put("website_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/propername/website_name.json"));
            this.config.put("app_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/propername/app_name.json"));

            HashMap<String, Set<Integer>> binaryProperNameDict = new HashMap<>();
            binaryProperNameDict.put("word_list", BinaryDictionary.getWords("cfg/entitycfg/common/propername/all_proper_name.bin"));
            this.config.put("all_proper_name", new JSONObject(binaryProperNameDict));

//            this.config.put("all_proper_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/vfast/all_proper_name.json"));
            this.config.put("special_full_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/propername/special_full_name.json"));
            this.config.put("person_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/propername/person_name.json"));
            this.config.put("serial_number", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/serial_number.json"));
            this.config.put("month", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/month.json"));
            this.config.put("year", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/year.json"));
            this.config.put("month_year_count", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/month_year_count.json"));

            this.config.put("number", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/number_regex.json"));

            this.config.put("number_punctuation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/number_punctuation.json"));
            this.config.put("time", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/time.json"));
            this.config.put("sqrt_calculation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/sqrt_calculation.json"));
            this.config.put("unit", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/text/unit.json"));
            this.config.put("match_result", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/text/unit.json"));

            this.config.put("character_lexicon", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/text/character_lexicon.json"));
            this.config.put("abbreviation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/text/abbreviation.json"));
            this.config.put("segment", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/text/segment.json"));
            this.config.put("written_serial_number_character", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/written_serial_number_character.json"));
            this.config.put("match_result", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/match_result.json"));

            this.config.put("sentence_punctuation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/text/sentence_punctuation.json"));

            this.config.put("output_smoothing", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/text/output_smoothing.json"));
        } catch (JSONException e) {
            System.out.println("Error loading config");
            e.printStackTrace();
        }
    }
}
