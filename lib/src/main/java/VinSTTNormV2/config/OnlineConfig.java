package VinSTTNormV2.config;

import VinSTTNorm.asrnormalizer.config.normalizerconfig.BaseNormalizerConfig;
import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import VinSTTNorm.asrnormalizer.utilities.binaryencoder.BinaryDictionary;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Set;

public class OnlineConfig extends BaseNormalizerConfig {
    public OnlineConfig() {
        try {
            HashMap<String, Set<Integer>> binaryProperNameDict = new HashMap<>();

            //Exotic
            this.config.put("lexicon", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/lexicon.json"));
            this.config.put("character_lexicon", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/character_lexicon.json"));
            this.config.put("abbreviation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/abbreviation.json"));
            this.config.put("segment", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/segment.json"));
            this.config.put("written_serial_number_character", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/written_serial_number_character.json"));

            //Link
            this.config.put("website_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/website_name.json"));

            //Number
            this.config.put("serial_number", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/serial_number.json"));
            this.config.put("month", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/month.json"));
            this.config.put("year", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/year.json"));
            this.config.put("month_year_count", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/month_year_count.json"));
            this.config.put("number", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number01.json"));
//            this.config.put("number", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number.json"));
            this.config.put("number_punctuation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number_punctuation.json"));
            this.config.put("air_conditioner", ConfigUtilities.getConfigFromFile("cfg/entitycfg/vfast/air_conditioner.json"));
            this.config.put("time", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/time.json"));
            this.config.put("unit", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/unit.json"));

            //Global
            this.config.put("output_smoothing", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/output_smoothing.json"));

        } catch (JSONException e) {
            System.out.println("Error loading config");
            e.printStackTrace();
        }
    }
}
