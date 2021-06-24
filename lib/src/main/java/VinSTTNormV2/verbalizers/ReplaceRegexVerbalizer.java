package VinSTTNormV2.verbalizers;

import VinSTTNormV2.utilities.RegexConfig;
import VinSTTNormV2.taggers.ReplaceRegexTagger;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.Map;

abstract public class ReplaceRegexVerbalizer extends BaseVerbalizer {

    private Map<String, String> tokenMap;
    protected List<RegexConfig> cacheRegexConfigArray;
    protected Map<String, String> cacheTokenMap;

    abstract public ReplaceRegexTagger getSampleExtractor(JSONObject config);


    public ReplaceRegexVerbalizer(JSONObject config){
        super(config);
        ReplaceRegexTagger replaceRegexExtractor = this.getSampleExtractor(config);
        replaceRegexExtractor.loadRegexConfigList();
        this.tokenMap = replaceRegexExtractor.getTokenMap();
        this.cacheTokenMap = replaceRegexExtractor.getCacheTokenMap();
    }



    public boolean isCaseSensitive(){
        return true;
    }

    @Override
    public String doNorm(String spokenFormEntityString){
        if (!this.isCaseSensitive()){
            spokenFormEntityString = spokenFormEntityString.toLowerCase(Locale.ROOT);
        }
//        System.out.println(tokenMap);
//        System.out.println(spokenFormEntityString);
        String result = tokenMap.containsKey(spokenFormEntityString) ? tokenMap.get(spokenFormEntityString) : spokenFormEntityString;
        return result;
    }
}
