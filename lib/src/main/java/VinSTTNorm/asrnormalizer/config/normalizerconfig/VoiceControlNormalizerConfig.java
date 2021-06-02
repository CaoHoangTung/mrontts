package VinSTTNorm.asrnormalizer.config.normalizerconfig;

import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import org.json.JSONException;

public class VoiceControlNormalizerConfig extends BaseNormalizerConfig {
    private final String TAG = "NORMALIZER_CONFIG";

    public VoiceControlNormalizerConfig() {
        try {
            this.config.put("lexicon", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/lexicon.json"));
            this.config.put("website_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/website_name.json"));
            this.config.put("app_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/app_name.json"));

            this.config.put("country", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/country.json"));
            this.config.put("province", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/province.json"));
            this.config.put("district", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/district.json"));
            this.config.put("poi", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/poi.json"));

            this.config.put("special_full_name", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/special_full_name.json"));
            this.config.put("serial_number", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/serial_number.json"));
            this.config.put("month", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/month.json"));
            this.config.put("year", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/year.json"));
            this.config.put("month_year_count", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/month_year_count.json"));
            this.config.put("number", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number01.json"));
            this.config.put("sqrt_calculation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/sqrt_calculation.json"));
            this.config.put("number_punctuation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/number_punctuation.json"));
            this.config.put("time", ConfigUtilities.getConfigFromFile("cfg/entitycfg/vassistant/time.json"));
            this.config.put("unit", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/unit.json"));
            this.config.put("character_lexicon", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/character_lexicon.json"));
            this.config.put("abbreviation", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/abbreviation.json"));
            this.config.put("segment", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/segment.json"));
            this.config.put("written_serial_number_character", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/written_serial_number_character.json"));

            this.config.put("output_smoothing", ConfigUtilities.getConfigFromFile("cfg/entitycfg/common/output_smoothing.json"));
        } catch (JSONException e) {
            System.out.println("Error loading config");
            e.printStackTrace();
        }
    }
}
