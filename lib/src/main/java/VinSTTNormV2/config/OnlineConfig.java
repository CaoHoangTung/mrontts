package VinSTTNormV2.config;

import VinSTTNorm.asrnormalizer.config.normalizerconfig.BaseNormalizerConfig;
import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Set;

public class OnlineConfig extends BaseNormalizerConfig {
    public OnlineConfig() {
        try {
            HashMap<String, Set<Integer>> binaryProperNameDict = new HashMap<>();

            //Exotic
            this.config.put("lexicon", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/text/lexicon.json"));
            this.config.put("character_lexicon", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/text/character_lexicon.json"));
            this.config.put("abbreviation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/text/abbreviation.json"));
            this.config.put("segment", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/text/segment.json"));
            this.config.put("written_serial_number_character", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/written_serial_number_character.json"));

            //Link
            this.config.put("website_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/propername/website_name.json"));

            //Number
            this.config.put("serial_number", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/serial_number.json"));
            this.config.put("month", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/month.json"));
            this.config.put("day", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/day.json"));
            this.config.put("year", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/year.json"));
            this.config.put("month_year_count", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/month_year_count.json"));
            this.config.put("number", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/number_fst.json"));
//            this.config.put("number", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number_fst.json"));
            this.config.put("number_punctuation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/number_punctuation.json"));
            this.config.put("air_conditioner", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/air_conditioner.json"));
            this.config.put("time", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number/time.json"));
            this.config.put("unit", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/text/unit.json"));

            //Global
            this.config.put("output_smoothing", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/text/output_smoothing.json"));

        } catch (JSONException e) {
            System.out.println("Error loading config");
            e.printStackTrace();
        }
    }
}
