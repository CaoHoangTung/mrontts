package VinSTTNorm.asrnormalizer.abstractentity;

import VinSTTNorm.asrnormalizer.config.RegexConfig;
import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import org.json.JSONArray;
import org.json.JSONObject;

abstract public class SimpleRegexEntity extends RegexBaseEntity {
    public SimpleRegexEntity(JSONObject config) {
        super(config);
    }

    @Override
    public RegexConfig[] loadRegexConfigList() {
        RegexConfig[] regexConfigs;

        try {
            String[] keys = {this.getType()};
            JSONObject localConfig = ConfigUtilities.getConfigItem(this.getGlobalConfig(), keys);

            JSONArray regexConfigJSON = (JSONArray) localConfig.get("patterns");

            regexConfigs = new RegexConfig[regexConfigJSON.length()];


            for (int i = 0 ; i < regexConfigJSON.length() ; i++) {
                RegexConfig regexConfig;
                regexConfig = ConfigUtilities.getRegexConfigFromJSONObject(regexConfigJSON.getJSONObject(i));

                regexConfig.setPrefix(this.processRegexConfigString(regexConfig.getPrefix()));
                regexConfig.setPostfix(this.processRegexConfigString(regexConfig.getPostfix()));
                regexConfig.setPattern(this.processRegexConfigString(regexConfig.getPattern()));

                regexConfigs[i] = regexConfig;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Error loading config for %s", this.getType()));
            regexConfigs = new RegexConfig[0];
        }
        return regexConfigs;
    }
}
