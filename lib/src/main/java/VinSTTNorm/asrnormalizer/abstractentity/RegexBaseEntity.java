package VinSTTNorm.asrnormalizer.abstractentity;

import VinSTTNorm.asrnormalizer.BaseEntity;
import VinSTTNorm.asrnormalizer.config.RegexConfig;
import VinSTTNorm.asrnormalizer.entityobject.EntityObject;
import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import VinSTTNorm.asrnormalizer.utilities.Utilities;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

abstract public class RegexBaseEntity extends BaseEntity {
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
    public String processRegexConfigString(String s) {
        String[] synonymRegexList = {
                "###\\w+(\\.\\w+)*###", // type 0: the synonym points to a RegexConfig object
                "##\\$\\w+(\\.\\w+)*\\$##", // type 1: the synonym points to a dict object. Will need to convert this to regex string
        };

        List<EntityObject> matchGroups = new ArrayList<>();

        for (int regexIdx = 0 ; regexIdx < synonymRegexList.length ; regexIdx++) {
            String synonymRegex = synonymRegexList[regexIdx];

            List<EntityObject> currentMatchGroups = Utilities.getMatchGroups(s, synonymRegex, REGEX_SYNONYM);

            for (int idx = 0; idx < currentMatchGroups.size(); idx++) {
                EntityObject entityObject = currentMatchGroups.get(idx);

                int characterStart = entityObject.characterStart;
                int characterEnd = entityObject.characterEnd;
                String subText = entityObject.text;

                String[] keys = subText.substring(3, subText.length() - 3).split("\\.");

                JSONObject configJSON = ConfigUtilities.getConfigItem(this.globalConfig, keys);

                switch (regexIdx) {
                    case 0: { // RegexConfig object
                        RegexConfig regexConfig = ConfigUtilities.getRegexConfigFromJSONObject(configJSON);

                        String replacement = regexConfig.toString();
                        currentMatchGroups.set(idx, new EntityObject(characterStart, characterEnd, this.getType(), subText, replacement));
                        matchGroups.add(currentMatchGroups.get(idx));
                        break;
                    }
                    case 1: { // dict object
                        Map<String, String> map = ConfigUtilities.getMapFromJSONObject(configJSON);

                        String replacement = Utilities.buildRegexStringFromMap(map);
                        currentMatchGroups.set(idx, new EntityObject(characterStart, characterEnd, this.getType(), subText, replacement));
                        matchGroups.add(currentMatchGroups.get(idx));
                        break;
                    }
                }
            }
        }

        EntityObject[] spanTextEntities = new EntityObject[matchGroups.size()];
        matchGroups.toArray(spanTextEntities);
        String result = Utilities.replaceString(s, spanTextEntities);

        return result;
    }

    public RegexBaseEntity(JSONObject config) {
        super(config);
    }

    /**
     * Get a list of entities
     * @param text
     * @return
     */
    @Override
    public EntityObject[] getEntities(String text) {
        ArrayList<EntityObject> result = new ArrayList<>();

        RegexConfig[] regexConfigList;

        if (this.cacheRegexConfigList != null) {
            regexConfigList = this.cacheRegexConfigList;
        } else {
            regexConfigList = this.loadRegexConfigList();
        }

        for (RegexConfig regexConfig : regexConfigList) {
            String regexString = String.format("%s%s%s",
                    regexConfig.getPrefix(), regexConfig.getPattern(), regexConfig.getPostfix());
            List<EntityObject> groups = Utilities.getMatchGroups(text, regexString, this.getType(), regexConfig.getGroup());

            for (EntityObject entityObject : groups) {
                int characterStart = entityObject.characterStart;
                int characterEnd = entityObject.characterEnd;
                String subText = entityObject.text;

                String[] prefixTokens = text.substring(0, characterStart).split(" ");
                String[] postfixTokens = text.substring(Math.min(text.length(), characterEnd + 2)).split(" "); // postfixToken would be [token1, token2...]

                if (!this.isException(entityObject.text, prefixTokens, postfixTokens)) {
                    result.add(new EntityObject(characterStart, characterEnd, this.getType(), subText, this.normEntity(subText)));
                }
            }
        }
        EntityObject[] resultStringArray = new EntityObject[result.size()];
        result.toArray(resultStringArray);

        return resultStringArray;
    }

    protected String getSpanFromContext(String spokenFormEntity, String[] contextLeft, String[] contextRight, int spanLen, int leftContextSpanLen) {
        int rightContextSpanLen = spanLen - leftContextSpanLen - 1;

        StringBuilder spanStringBuilder = new StringBuilder();
        for (int leftContextIdx = Math.max(0, contextLeft.length-leftContextSpanLen) ; leftContextIdx < contextLeft.length ; leftContextIdx++) {
            spanStringBuilder.append(contextLeft[leftContextIdx]).append(' ');
        }
        spanStringBuilder.append(spokenFormEntity).append(' ');
        for (int rightContextIdx = 0 ; rightContextIdx < Math.min(contextRight.length, rightContextSpanLen) ; rightContextIdx++) {
            spanStringBuilder.append(contextRight[rightContextIdx]).append(' ');
        }
        spanStringBuilder.setLength(spanStringBuilder.length()-1);

        return spanStringBuilder.toString();
    }

    @Override
    public boolean isException(String spokenFormEntity, String[] contextLeft, String[] contextRight) {
        return false;
    }
}
