package VinSTTNormV2.verbalizers.number.date;

import VinSTTNormV2.verbalizers.number.CommonNumberVerbalizer;
import org.json.JSONObject;

import java.util.Locale;

public class YearVerbalizer extends CommonNumberVerbalizer {
    private static String TAG = "YEAR_NORMALIZER";

    public YearVerbalizer(JSONObject config){
        super(config);
    }

    @Override
    public String doNorm(String spokenFormEntityString){
        spokenFormEntityString = spokenFormEntityString.toLowerCase(Locale.ROOT);
        String normedYear = super.doNorm(spokenFormEntityString);

        if (normedYear.length() > 4 || normedYear.length() < 3)
            return spokenFormEntityString;
        if (normedYear.length() == 4 && normedYear.charAt(0) != '1' && normedYear.charAt(0) != '2')
            return spokenFormEntityString;

        return normedYear;
    }
}
