package VinSTTNormV2.spanExtractor;

import VinSTTNormV2.config.RegexConfig;
import VinSTTNormV2.utilities.ConfigUtilities;
import VinSTTNormV2.utilities.Utilities;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract public class RegexBaseExtractor extends BaseExtractor {
    private final String REGEX_SYNONYM = "regex_synonym";

    protected RegexConfig[] cacheRegexConfigList;

    /**
     * Load the regex config list
     * @return
     */
    abstract public RegexConfig[] loadRegexConfigList();


    /**
     * Handle customize synonyms. Eg: ###positive_integer_pattern###
     * @param s
     * @return
     */

    public String processRegexConfigString(String s){
        String[] synonymRegexList = {
                "###\\w+(\\.\\w+)*###", // type 0: the synonym points to a RegexConfig object
                "##\\$\\w+(\\.\\w+)*\\$##", // type 1: the synonym points to a dict object. Will need to convert this to regex string
        };

        List<SpanObject> matchGroups = new ArrayList<>();
        for (int regexIdx = 0; regexIdx < synonymRegexList.length; regexIdx++){
            String synonymRegex = synonymRegexList[regexIdx];

            List<SpanObject> currentMatchGroups = Utilities.getMatchGroups(s, synonymRegex, REGEX_SYNONYM);

            for (int idx =0; idx < currentMatchGroups.size(); idx++){
                SpanObject spanObject = currentMatchGroups.get(idx);

                int charaterStart = spanObject.characterStart;
                int characterEnd = spanObject.characterEnd;

                String subText = spanObject.text;

                String[] keys = subText.substring(3, subText.length()-3).split("\\.");

                JSONObject configJSON = ConfigUtilities.getConfigItem(this.globalConfig, keys);

                switch (regexIdx){
                    case 0:{
                        RegexConfig regexConfig = ConfigUtilities.getRegexConfigFromJSONObject(configJSON);

                        String replacement = regexConfig.toString();
                        currentMatchGroups.set(idx, new SpanObject(charaterStart, characterEnd, this.getType(), subText, replacement));
                        matchGroups.add(currentMatchGroups.get(idx));
                        break;
                    }
                    case 1:{
                        Map<String, String> map = ConfigUtilities.getMapFromJSONObject(configJSON);

                        String replacement = Utilities.buildRegexStringFromMap(map);
                        currentMatchGroups.set(idx, new SpanObject(charaterStart, characterEnd, this.getType(), subText, replacement));
                        matchGroups.add(currentMatchGroups.get(idx));
                        break;
                    }
                }
            }
        }
        SpanObject[] spanText = new SpanObject[matchGroups.size()];
        matchGroups.toArray(spanText);
        String result = Utilities.replaceString(s, spanText);

        return result;
    }

    public RegexBaseExtractor(JSONObject config){
        super(config);
    }

    /**
     * Get a list of entities
     * @param text
     * @return
     */

    @Override
    public SpanObject[] getSpans(String text){
        ArrayList<SpanObject> result = new ArrayList<>();
        RegexConfig[] regexConfigList;

        if (this.cacheRegexConfigList != null){
            regexConfigList = this.cacheRegexConfigList;
        } else {
            regexConfigList = this.loadRegexConfigList();
        }

        for (RegexConfig regexConfig : regexConfigList){
            String regexString = String.format("%s%s%s",
                    regexConfig.getPrefix(), regexConfig.getPattern(), regexConfig.getPostfix());
            List<SpanObject> groups = Utilities.getMatchGroups(text, regexString, this.getType(), regexConfig.getGroup());
            for (SpanObject spanObject : groups){
                int charaterStart = spanObject.characterStart;
                int characterEnd = spanObject.characterEnd;
                String subText = spanObject.text;

                String[] prefixTokens = text.substring(0, charaterStart).split(" ");
                String[] postfixTokens = text.substring(Math.min(text.length(), characterEnd+2)).split(" ");

                if (!this.isException(spanObject.text, prefixTokens, postfixTokens)){
                    result.add(new SpanObject(charaterStart, characterEnd, this.getType(), subText, ""));
                }
            }

        }
        SpanObject[] resultStringArray = new SpanObject[result.size()];
        result.toArray(resultStringArray);
        return resultStringArray;

    }

    protected  String getSpanFromContext(String spokenFormEntity, String[] contextLeft, String[] contextRight, int spanLen, int leftContextSpanLen){
        int rightContextSpanLen = spanLen - leftContextSpanLen - 1;

        StringBuilder spanStringBuilder = new StringBuilder();
        for (int leftContextIdx = Math.max(0, contextLeft.length-leftContextSpanLen); leftContextIdx < contextLeft.length; leftContextIdx++){
            spanStringBuilder.append(contextLeft[leftContextIdx]).append(' ');
        }
        spanStringBuilder.append(spokenFormEntity).append(' ');
        for (int rightContextIdx = 0; rightContextIdx < Math.min(contextRight.length, rightContextSpanLen); rightContextIdx++){
            spanStringBuilder.append(contextRight[rightContextIdx]).append(' ');
        }
        spanStringBuilder.setLength(spanStringBuilder.length());

        return spanStringBuilder.toString();
    }

    @Override
    public boolean isException(String spokenFormEntity, String[] contextLeft, String[] contextRight){
        return false;
    }
}
