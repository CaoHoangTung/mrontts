package VinSTTNorm.asrnormalizer.numerical;


import VinSTTNorm.asrnormalizer.abstractentity.SimpleRegexEntity;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class SerialNumberRegexEntity extends SimpleRegexEntity {
    private static String TAG = "SERIAL_NUMBER_ENTITY";

    private Map<String, Integer> stringToDigit;

    public SerialNumberRegexEntity(JSONObject config) {
        super(config);
        stringToDigit = new LinkedHashMap<>();

        stringToDigit.put("không", 0);
        stringToDigit.put("lẻ", 0);
        stringToDigit.put("linh", 0);
        stringToDigit.put("mốt", 1);
        stringToDigit.put("một", 1);
        stringToDigit.put("hai", 2);
        stringToDigit.put("hăm", 2);
        stringToDigit.put("ba", 3);
        stringToDigit.put("bốn", 4);
        stringToDigit.put("tư", 4);
        stringToDigit.put("năm", 5);
        stringToDigit.put("lăm", 5);
        stringToDigit.put("nhăm", 5);
        stringToDigit.put("sáu", 6);
        stringToDigit.put("bảy", 7);
        stringToDigit.put("bẩy", 7);
        stringToDigit.put("tám", 8);
        stringToDigit.put("chín", 9);
    }
    
    @Override
    public String getType() {
        return "serial_number";
    }

    @Override
    public String normEntity(String spokenFormEntityString) {
        String prefix = "";

        if (spokenFormEntityString.contains("trừ") || spokenFormEntityString.contains("âm"))
            prefix = "-";

        if (spokenFormEntityString.contains("cộng") || spokenFormEntityString.contains("dương"))
            prefix = "+";

        spokenFormEntityString = spokenFormEntityString.replaceAll("(âm |trừ |dương |cộng )", "");

        String[] tokens = spokenFormEntityString.split(" ");

        StringBuilder result = new StringBuilder();
        result.append(prefix);

        for (String token : tokens) {
           result.append(stringToDigit.get(token));
        }

        return result.toString();
    }


    @Override
    public boolean isException(String spokenFormEntity, String[] contextLeft, String[] contextRight) {
        return false;
    }
}
