package VinSTTNormV2.verbalizers.number.date;

import VinSTTNormV2.verbalizers.number.CommonNumberVerbalizer;
import org.json.JSONObject;

public class DayVerbalizer extends CommonNumberVerbalizer {
    private static String TAG = "day_normalizer";

    public DayVerbalizer(JSONObject config){ super(config);}

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
