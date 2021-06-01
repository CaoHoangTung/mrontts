package VinSTTNormV2.spanNormalizer.number.date;

import VinSTTNormV2.spanNormalizer.number.CommonNumberNormalizer;
import org.json.JSONObject;

public class YearNormalizer extends CommonNumberNormalizer {
    private static String TAG = "YEAR_NORMALIZER";

    public YearNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public String doNorm(String spokenFormEntityString){
        String normedYear = super.doNorm(spokenFormEntityString);

        if (normedYear.length() > 4 || normedYear.length() < 3)
            return spokenFormEntityString;
        if (normedYear.length() == 4 && normedYear.charAt(0) != '1' && normedYear.charAt(0) != '2')
            return spokenFormEntityString;

        return normedYear;
    }
}
