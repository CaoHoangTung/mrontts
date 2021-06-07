package VinSTTNormV2.spanNormalizer.number.date;

import VinSTTNormV2.spanNormalizer.number.CommonNumberNormalizer;
import org.json.JSONObject;

public class DayNormalizer extends CommonNumberNormalizer {
    private static String TAG = "day_normalizer";

    public DayNormalizer(JSONObject config){ super(config);}

    @Override
    public String doNorm(String spokenFormEntityString){
        String normedDay = super.doNorm(spokenFormEntityString);

        if (normedDay.length() > 2)
            return spokenFormEntityString;
        if (normedDay.length() == 2 && normedDay.charAt(0) >3)
            return spokenFormEntityString;
        if (normedDay.length() == 2 && normedDay.charAt(0) == 3 && normedDay.charAt(1) > 1)
            return spokenFormEntityString;

        return normedDay;
    }
}
