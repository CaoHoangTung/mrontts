package VinSTTNorm.asrnormalizer.vfast;

import VinSTTNorm.asrnormalizer.abstractentity.SimpleRegexEntity;
import org.json.JSONObject;

/**
 * x độ rưỡi => x.5 độ
 */
public class VinFastAirConditionerNumberEntity extends SimpleRegexEntity {

    public VinFastAirConditionerNumberEntity(JSONObject config) {
        super(config);
    }

    @Override
    public String getType() {
        return "air_conditioner";
    }

    @Override
    public String normEntity(String spokenFormEntityString) {
        String[] tokens = spokenFormEntityString.split(" ");
        if (tokens.length > 1 && tokens[1].equals("độ")) {
            return String.format("%s.5 độ", tokens[0]);
        } else {
            return String.format("%s.5", tokens[0]);
        }
    }
}
