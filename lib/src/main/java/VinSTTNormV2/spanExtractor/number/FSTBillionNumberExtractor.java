package VinSTTNormV2.spanExtractor.number;

import VinSTTNorm.asrnormalizer.numerical.NumberFSTEntity;
import org.json.JSONObject;

public class FSTBillionNumberExtractor extends FSTNumberExtractor {
    public FSTBillionNumberExtractor(JSONObject config) {
        super(config);
    }

    @Override
    public String getNumberType() {
        return "billion_digit";
    }
}
