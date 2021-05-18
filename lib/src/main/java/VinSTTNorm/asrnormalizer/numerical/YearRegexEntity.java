package VinSTTNorm.asrnormalizer.numerical;


import org.json.JSONObject;

public class YearRegexEntity extends NumberRegexEntity {
    private static String TAG = "YEAR_ENTITY";

    public YearRegexEntity(JSONObject config) {
        super(config);
    }
    
    @Override
    public String getType() {
        return "year";
    }

    @Override
    public String normEntity(String spokenFormEntityString) {
        String normedYear = super.normEntity(spokenFormEntityString);

        // limit the years that can be normed
        if (normedYear.length() > 4 || normedYear.length() < 3)
            return spokenFormEntityString;
        if (normedYear.length() == 4 && normedYear.charAt(0) != '1' && normedYear.charAt(0) != '2')
            return spokenFormEntityString;

        return normedYear;
    }
}
