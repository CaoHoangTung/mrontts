package VinSTTNorm.asrnormalizer.utilities;

import VinSTTNorm.asrnormalizer.entityobject.EntityObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities for the package
 */
public class Utilities {
    static private String TAG = "UTILITIES";

    /**
     * @param path - file path
     * @return list of string. Each string is read from a line
     */
    static public String[] loadLinesFromFile(String path) {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path)));
            String line = bufferedReader.readLine();
            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            String[] result = new String[lines.size()];
            lines.toArray(result);
            return result;

        } catch (Exception e) {
            System.out.println(String.format("Error loading file %s . Returning empty list", path));
            e.printStackTrace();
            return new String[0];
        }
    }

    static public LinkedHashMap getMappingFromFile(String path, boolean caseSensitive) {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path)));
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] lineItems = line.split(",");
                String key = lineItems[0];
                String value = lineItems[1];

                if (!caseSensitive) {
                    key = key.toLowerCase(Locale.ROOT);
                }

                result.put(key, value);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(String.format("Error loading file %s . Returning empty list", path));
            e.printStackTrace();
        }
        return result;
    }

    static public LinkedHashMap getMappingFromFile(String path) {
        return getMappingFromFile(path, true);
    }

    /**
     * Build regex from a map. This map is loaded from the getMappingFromFile function
     * @param map
     * @return
     */
    static public String buildRegexStringFromMap(Map map) {
        StringBuilder result = new StringBuilder("(");

        try {
            for (Object keyObj : map.keySet()) {
                String key = keyObj.toString();
                result.append(key+"|");
            }
            if (result.length() > 1) {
                result.deleteCharAt(result.length()-1);
            }
        } catch (Exception e) {
            System.out.println(String.format("Error building regex from map . Returning empty list"));
            e.printStackTrace();
        }

        result.append(")");

        if (result.toString().equals("()"))
            return "";

        return result.toString();
    }

    /**
     * Build regex from a list. This list is loaded from the loadLinesFromFile function
     * @param lines
     * @return
     */
    static public String buildRegexStringFromList(String[] lines) {
        StringBuilder result = new StringBuilder("(");

        try {
            for (String line : lines) {
                result.append(line+"|");
            }

            if (result.length() > 1) {
                result.deleteCharAt(result.length()-1);
            }
        } catch (Exception e) {
            System.out.println(String.format("Error building regex from map . Returning empty list"));
            e.printStackTrace();
        }

        result.append(")");

        if (result.toString().equals("()"))
            return "";

        return result.toString();
    }

    static public HashSet getSetFromFile(String path, boolean caseSensitive) {
        HashSet result = new HashSet();
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(path)));
            String line = bufferedReader.readLine();
            while (line != null) {
                if (!caseSensitive)
                    line = line.toLowerCase();
                result.add(line);
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(String.format("Error loading file %s . Returning empty set", path));
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Join string from tokens.
     * Example: {"a","b","c"}," " => "a b c"
     * @param tokens
     * @return
     */
    static public String joinString(String[] tokens, String sep) {
        StringBuilder result = new StringBuilder();

        for (String token: tokens) {
            result.append(token + sep);
        }
        if (result.length() >= sep.length()) {
            result.deleteCharAt(result.length() - sep.length());
        }

        return result.toString();
    }

    /**
     * Return a list of EntityObject - match of regexString on text
     * @param text
     * @param regexString
     * @return
     */
    static public List<EntityObject> getMatchGroups(String text, String regexString, String type, int group) {
        List<EntityObject> result = new ArrayList<>();

        Pattern pattern = Pattern.compile(regexString);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            try {
                int beginIndex = matcher.start(group);
                int endIndex = matcher.end(group);

                if (beginIndex == endIndex)
                    continue;

//                if (text.charAt(endIndex - 1) == ' ')
//                    endIndex--;

                String subText = text.substring(beginIndex, endIndex);

                if (beginIndex < endIndex) {
                    result.add(new EntityObject(beginIndex, endIndex - 1, type, subText));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    static public List<EntityObject> getMatchGroups(String text, String regexString, String type) {
        return getMatchGroups(text, regexString, type, 0);
    }

    static public String replaceString(String text, EntityObject[] spanTextEntities) {
        Arrays.sort(spanTextEntities);

//        for (EntityObject span : spanTextEntities) {
//            System.out.println("SPAN: " + span);
//        }


        StringBuilder normedTextBuilder = new StringBuilder();
        int idx = 0;
        int entityId = 0;

        while (idx < text.length()) {
            if (entityId < spanTextEntities.length) {
                EntityObject currentEntity = spanTextEntities[entityId];
                if (idx < currentEntity.characterStart || idx > currentEntity.characterEnd) {
                    normedTextBuilder.append(text.charAt(idx));
                } else if (idx == currentEntity.characterStart) {
                    normedTextBuilder.append(currentEntity.replacement);
                }

                if (idx >= currentEntity.characterEnd) {
                    entityId++;
                }
            } else {
                normedTextBuilder.append(text.charAt(idx));
            }
            idx++;
        }
        return normedTextBuilder.toString();
    }
}
