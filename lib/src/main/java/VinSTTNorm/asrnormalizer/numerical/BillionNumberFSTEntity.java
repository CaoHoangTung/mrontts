package VinSTTNorm.asrnormalizer.numerical;

import org.json.JSONObject;

public class BillionNumberFSTEntity extends NumberFSTEntity {
    public BillionNumberFSTEntity(JSONObject config) {
        super(config);
    }

    @Override
    public String getNumberType() {
        return "billion_digit";
    }
}
