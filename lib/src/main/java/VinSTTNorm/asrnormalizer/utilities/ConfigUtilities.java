package VinSTTNorm.asrnormalizer.utilities;

import VinSTTNorm.asrnormalizer.config.RegexConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ConfigUtilities {
    static public final String TAG = "CONFIG_UTILITIES";

    static public JSONObject getConfigFromFile(String path) {
        JSONObject config;
        BufferedReader bufferedReader;

        try {
            ClassLoader classLoader = ConfigUtilities.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(path);

            InputStreamReader fileStreamReader = new InputStreamReader(inputStream, "UTF-8");
            StringBuilder jsonStringBuilder;
            jsonStringBuilder = new StringBuilder("");

            bufferedReader = new BufferedReader(fileStreamReader);

            String line;
            do {
                line = bufferedReader.readLine();
                jsonStringBuilder.append(line).append("\n");
            } while (line != null);

            config  = new JSONObject(jsonStringBuilder.toString());

            bufferedReader.close();
            fileStreamReader.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            config = null;
        }

        return config;
    }

    static public JSONObject getConfigItem(JSONObject config, String[] keys) {
        JSONObject result = config;

        try {
            for (String key : keys) {
                result = (JSONObject) result.get(key);
            }
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
            System.out.println(TAG +  "Error getConfigItem. Keys = " + Arrays.toString(keys));
        }

        return result;
    }
    
    static public RegexConfig getRegexConfigFromJSONObject(JSONObject jsonObject) {
        RegexConfig result;
        try {
            String prefix = (String) jsonObject.get("prefix");
            String postfix = (String) jsonObject.get("postfix");
            String pattern = (String) jsonObject.get("pattern");
            int group = (Integer) jsonObject.get("group");
            result = new RegexConfig(prefix, postfix, pattern, group);
        } catch (Exception e) {
            result = RegexConfig.getDefault();
            e.printStackTrace();
            System.out.println(TAG +  "Error converting from JSONObject to RegexConfig");
        }
        return result;
    }


    static public Map getMapFromJSONObject(JSONObject jsonObject) {
        Iterator<String> dictKeys = jsonObject.keys();
        Map<String, String> result = new LinkedHashMap<>();

        try {
            while (dictKeys.hasNext()) {
                String key = dictKeys.next();
                String value = jsonObject.getString(key);
                result.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(TAG +  "Error converting from JSONObject to Map");
        }

        return result;
    }

    /**
     * Given a String array. Return a map which key = lowercase(value)
     * @param jsonArray ["H?? N???i", "H???i Ph??ng"]
     * @return {"h?? n???i": "H?? N???i", "h???i ph??ng": "H???i Ph??ng"}
     */
    static public Map getLowercaseToUppercaseMapFromJSONArray(JSONArray jsonArray) {
        Map<String, String> result = new LinkedHashMap<>();

        try {
            for (int idx = 0 ; idx < jsonArray.length() ; idx++) {
                String value = (String) jsonArray.get(idx);
                String key = value.toLowerCase(Locale.ROOT);
                result.put(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(TAG +  "Error converting from JSONArray to LowerUpperMap");
        }

        return result;
    }

    /**
     * Given a String array. Return a set of that array
     * @param jsonArray ["H?? N???i", "H???i Ph??ng"]
     * @return ("H?? N???i", "H???i Ph??ng")
     */
    static public <T> Set<T> getSetFromJSONArray(JSONArray jsonArray) {
        Set<T> result = new HashSet<>();

        try {
            for (int idx = 0 ; idx < jsonArray.length() ; idx++) {
                T value = (T) jsonArray.get(idx);
                result.add(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(TAG +  "Error converting from JSONArray to LowerUpperMap");
        }

        return result;
    }
}
