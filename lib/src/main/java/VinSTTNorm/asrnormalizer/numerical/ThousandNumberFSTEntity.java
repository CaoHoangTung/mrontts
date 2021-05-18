package VinSTTNorm.asrnormalizer.numerical;

import org.json.JSONObject;

public class ThousandNumberFSTEntity extends NumberFSTEntity {
    public ThousandNumberFSTEntity(JSONObject config) {
        super(config);
    }

    @Override
    public String getNumberType() {
        return "thousand_digit";
    }
}
