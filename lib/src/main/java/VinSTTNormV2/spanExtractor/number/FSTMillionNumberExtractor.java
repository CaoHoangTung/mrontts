package VinSTTNormV2.spanExtractor.number;

import VinSTTNorm.asrnormalizer.numerical.NumberFSTEntity;
import VinSTTNormV2.spanExtractor.BaseExtractor;
import org.json.JSONObject;

public class FSTMillionNumberExtractor extends FSTNumberExtractor {
    public FSTMillionNumberExtractor(JSONObject config) {
        super(config);
    }

    @Override
    public String getNumberType() {
        return "million_digit";
    }
}
