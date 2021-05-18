package VinSTTNorm.asrnormalizer.abstractentity;

import VinSTTNorm.asrnormalizer.config.RegexConfig;
import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

abstract public class ReplaceRegexEntity extends RegexBaseEntity {
    private Map<String, String> tokenMap;

    public ReplaceRegexEntity(JSONObject config) {
        super(config);
    }

    protected List<RegexConfig> cacheRegexConfigsArray;
    protected Map<String, String> cacheTokenMap;

    public boolean isCaseSensitive() {
        return true;
    }

    @Override
    public RegexConfig[] loadRegexConfigList() {
        tokenMap = new LinkedHashMap<>();

        RegexConfig[] regexConfigs;

        try {
            String[] keys = {this.getType()};
            JSONObject localConfig = ConfigUtilities.getConfigItem(this.getGlobalConfig(), keys);

            JSONArray regexConfigJSONArray = localConfig.getJSONArray("patterns");

            List<RegexConfig> regexConfigsArray;

            if (this.cacheRegexConfigsArray != null ) {
                regexConfigsArray = this.cacheRegexConfigsArray;
                this.tokenMap = this.cacheTokenMap;
            } else {
                regexConfigsArray = new ArrayList<>();
                for (int idx = 0 ; idx < regexConfigJSONArray.length() ; idx++) {
                    JSONObject regexConfigJSON = (JSONObject) regexConfigJSONArray.get(idx);
                    RegexConfig regexConfig = ConfigUtilities.getRegexConfigFromJSONObject(regexConfigJSON);
                    this.tokenMap = ConfigUtilities.getMapFromJSONObject(localConfig.getJSONObject("dict"));

                    regexConfig.setPrefix(this.processRegexConfigString(regexConfig.getPrefix()));
                    regexConfig.setPostfix(this.processRegexConfigString(regexConfig.getPostfix()));
                    regexConfig.setPattern(this.processRegexConfigString(regexConfig.getPattern()));

                    regexConfigsArray.add(regexConfig);
                }

                this.cacheRegexConfigsArray = regexConfigsArray;
                this.cacheTokenMap = this.tokenMap;
            }

            regexConfigs = regexConfigsArray.toArray(new RegexConfig[regexConfigsArray.size()]);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Error loading config for %s", this.getType()));
            regexConfigs = new RegexConfig[0];
        }
        return regexConfigs;
    }

    @Override
    public String normEntity(String spokenFormEntityString) {
        if (!this.isCaseSensitive()) {
            spokenFormEntityString = spokenFormEntityString.toLowerCase(Locale.ROOT);
        }

        String result = tokenMap.containsKey(spokenFormEntityString) ? tokenMap.get(spokenFormEntityString) : spokenFormEntityString;

        return result;
    }
}
