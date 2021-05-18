package VinSTTNorm.asrnormalizer.vkeyboard;

import VinSTTNorm.asrnormalizer.abstractentity.SimpleRegexEntity;
import org.json.JSONObject;

/**
 * x độ rưỡi => x.5 độ
 */
public class VKeyboardMatchResultNumberEntity extends SimpleRegexEntity {

    public VKeyboardMatchResultNumberEntity(JSONObject config) {
        super(config);
    }

    @Override
    public String getType() {
        return "match_result";
    }

    @Override
    public String normEntity(String spokenFormEntityString) {
        String[] tokens = spokenFormEntityString.split(" ");
        if (tokens.length == 2) {
            if (tokens[1].equals("đều")) {
                return String.format("%s-%s", tokens[0], tokens[0]);
            } else {
                return String.format("%s-%s", tokens[0], tokens[1]);
            }
        } else if (tokens.length == 1) {
            if (tokens[0].length() > 1) {
                return String.format("%s-%s",
                        tokens[0].substring(0, tokens[0].length()/2),
                        tokens[0].substring(tokens[0].length()/2)
                );
            }
        }
        return spokenFormEntityString;
    }
}
