package VinSTTNorm.asrnormalizer.numerical;


import org.json.JSONObject;

public class MonthYearCountRegexEntity extends NumberRegexEntity {
    private static String TAG = "MONTH_YEAR_COUNT_ENTITY";

    public MonthYearCountRegexEntity(JSONObject config) {
        super(config);
    }
    
    @Override
    public String getType() {
        return "month_year_count";
    }

    @Override
    public String normEntity(String spokenFormEntityString) {
        return super.normEntity(spokenFormEntityString);
    }
}
