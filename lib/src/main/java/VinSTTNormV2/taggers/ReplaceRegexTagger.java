package VinSTTNormV2.taggers;

import VinSTTNormV2.utilities.RegexConfig;
import VinSTTNormV2.utilities.ConfigUtilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

abstract public class ReplaceRegexTagger extends RegexBaseTagger {
    private Map<String, String> tokenMap;

    public ReplaceRegexTagger(JSONObject config){
        super(config);
    }

    protected List<RegexConfig> cacheRegexConfigsArray;
    protected Map<String, String> cacheTokenMap;

    public boolean isCaseSensitive(){
        return true;
    }

    @Override
    public RegexConfig[] loadRegexConfigList(){
        tokenMap = new LinkedHashMap<>();

        RegexConfig[] regexConfigs;

        try{
            String[] keys = {this.getType()};
            JSONObject localConfig = ConfigUtilities.getConfigItem(this.getGlobalConfig(), keys);

            JSONArray regexConfigJSONArray = localConfig.getJSONArray("patterns");

            List<RegexConfig> regexConfigsArray;
            if (this.cacheRegexConfigsArray != null ) {
                regexConfigsArray = this.cacheRegexConfigsArray;
                this.tokenMap = this.cacheTokenMap;
            } else {
                regexConfigsArray = new ArrayList<>();
                for (int idx = 0; idx < regexConfigJSONArray.length(); idx++){
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
//            System.out.println(regexConfigsArray);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("Error loading config for %s", this.getType()));
            regexConfigs = new RegexConfig[0];
        }
        return regexConfigs;
    }

    public Map<String, String> getTokenMap() {
        return tokenMap;
    }

    public List<RegexConfig> getCacheRegexConfigsArray() {
        return cacheRegexConfigsArray;
    }

    public Map<String, String> getCacheTokenMap() {
        return cacheTokenMap;
    }
}
