package VinSTTNorm.asrnormalizer.utilities;

public class StringUtilities {
    /**
     * Join string from tokens.
     * Example: {"a","b","c"}," " => "a b c"
     * @param tokens
     * @return
     */
    static public String join(String[] tokens, String delimiter) {
        StringBuilder result = new StringBuilder();
        for (int idx = 0 ; idx < tokens.length-1 ; idx++) {
            String token = tokens[idx];
            result.append(token).append(delimiter);
        }
        if (tokens.length > 0) {
            result.append(tokens[tokens.length-1]);
        }

        return result.toString();
    }
}
