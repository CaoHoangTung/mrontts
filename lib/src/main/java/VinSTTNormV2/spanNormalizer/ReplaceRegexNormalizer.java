package VinSTTNormV2.spanNormalizer;

import VinSTTNormV2.config.RegexConfig;
import VinSTTNormV2.spanExtractor.ReplaceRegexExtractor;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

abstract public class ReplaceRegexNormalizer extends BaseNormalizer{

    private Map<String, String> tokenMap;

    abstract public ReplaceRegexExtractor getSampleExtractor(JSONObject config);


    public ReplaceRegexNormalizer(JSONObject config){
        super(config);
        ReplaceRegexExtractor replaceRegexExtractor = this.getSampleExtractor(config);
        replaceRegexExtractor.loadRegexConfigList();
        this.tokenMap = replaceRegexExtractor.getTokenMap();
    }

    protected List<RegexConfig> cacheRegexConfigArray;
    protected Map<String, String> cacheTokenMap;

    public boolean isCaseSensitive(){
        return true;
    }

    @Override
    public String doNorm(String spokenFormEntityString){
        if (!this.isCaseSensitive()){
            spokenFormEntityString = spokenFormEntityString.toLowerCase(Locale.ROOT);
        }

        String result = tokenMap.containsKey(spokenFormEntityString) ? tokenMap.get(spokenFormEntityString) : spokenFormEntityString;
        return result;
    }
}
