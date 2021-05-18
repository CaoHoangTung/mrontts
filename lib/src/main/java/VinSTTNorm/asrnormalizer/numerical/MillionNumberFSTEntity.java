package VinSTTNorm.asrnormalizer.numerical;

import org.json.JSONObject;

public class MillionNumberFSTEntity extends NumberFSTEntity {
    public MillionNumberFSTEntity(JSONObject config) {
        super(config);
    }

    @Override
    public String getNumberType() {
        return "million_digit";
    }
}
