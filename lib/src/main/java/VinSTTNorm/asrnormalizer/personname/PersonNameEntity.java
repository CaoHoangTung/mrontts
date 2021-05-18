package VinSTTNorm.asrnormalizer.personname;

import VinSTTNorm.asrnormalizer.BaseEntity;
import VinSTTNorm.asrnormalizer.entityobject.EntityObject;
import VinSTTNorm.asrnormalizer.utilities.ConfigUtilities;
import VinSTTNorm.asrnormalizer.utilities.StringUtilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class PersonNameEntity extends BaseEntity {
    protected final String TAG = "map_base_entity";

    protected int MIN_WINDOW_SIZE = 2;
    protected int MAX_WINDOW_SIZE = 4;

    private Map<String, String> firstNameMap;
    private Map<String, String> middleNameMap;
    private Map<String, String> lastNameMap;

    public PersonNameEntity(JSONObject config) {
        super(config);
        this.init();
    }

    public void init() {
        try {
            String[] keys = {"person_name", "dict"};
            JSONObject personNameJSON = ConfigUtilities.getConfigItem(this.getGlobalConfig(), keys);

            JSONArray firstNameJSONArray = personNameJSON.getJSONArray("first_name");
            JSONArray middleNameJSONArray = personNameJSON.getJSONArray("middle_name");
            JSONArray lastNameJSONArray = personNameJSON.getJSONArray("last_name");

            this.firstNameMap = ConfigUtilities.getLowercaseToUppercaseMapFromJSONArray(firstNameJSONArray);
            this.middleNameMap = ConfigUtilities.getLowercaseToUppercaseMapFromJSONArray(middleNameJSONArray);
            this.lastNameMap = ConfigUtilities.getLowercaseToUppercaseMapFromJSONArray(lastNameJSONArray);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(TAG +  "Error init() PersonNameEntity");
        }
    }

    /**
     * Get a list of entities
     * @param text
     * @return
     */
    @Override
    public EntityObject[] getEntities(String text) {
        ArrayList<EntityObject> result = new ArrayList<>();

        String[] tokens = text.split(" ");

        int startIdx = 0;
        int stringStartIdx = 0;
        while (startIdx <= tokens.length - MIN_WINDOW_SIZE) {
            for (int wSize = MAX_WINDOW_SIZE ; wSize >= MIN_WINDOW_SIZE  ; wSize--) {
                int endIdx = startIdx + wSize - 1;
                if (endIdx >= tokens.length) {
                    continue;
                }

                String[] candidate = Arrays.copyOfRange(tokens, startIdx, endIdx+1);
                String candidateString = StringUtilities.join(candidate, " ");

                if (this.isNameCandidate(candidate)) {
                    int stringEndIdx = stringStartIdx + candidateString.length() - 1;

                    result.add(new EntityObject(stringStartIdx, stringEndIdx, this.getType(), candidateString, this.normEntity(candidateString)));

                    startIdx = endIdx;
                    stringStartIdx = stringEndIdx - candidate[candidate.length-1].length() + 1;

                    break;
                }
            }

            stringStartIdx += tokens[startIdx].length() + 1;
            startIdx++;
        }

        EntityObject[] resultStringArray = new EntityObject[result.size()];
        result.toArray(resultStringArray);

        return resultStringArray;
    }

    public boolean isNameCandidate(String[] tokens) {
        Map[][] nameConfigs = {
                {lastNameMap, middleNameMap, firstNameMap},
                {lastNameMap, middleNameMap, middleNameMap, firstNameMap},
        };
        boolean check = false;
        for (Map[] config : nameConfigs) {
            if (config.length != tokens.length)
                continue;
            else {
                boolean configIsValid = true;
                for (int idx = 0 ; idx < tokens.length ; idx++) {
                    String token = tokens[idx];
                    Map configSet = config[idx];

                    try {
                        if (!configSet.containsKey(token)) {
                            configIsValid = false;
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        configIsValid = false;
                        break;
                    }
                }
                if (configIsValid) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public String getType() {
        return "person_name";
    }

    @Override
    public String normEntity(String spokenFormEntityString) {
        String[] tokens = spokenFormEntityString.split(" ");
        StringBuilder result = new StringBuilder();

        for (int idx = 0 ; idx < tokens.length-1 ; idx++) {
            String token = tokens[idx];
            result.append(Character.toUpperCase(token.charAt(0))).append(token.substring(1));
            result.append(' ');
        }
        if (tokens.length > 0)
            result.append(Character.toUpperCase(tokens[tokens.length-1].charAt(0))).append(tokens[tokens.length-1].substring(1));

        return result.toString();
    }
}
