package VinSTTNormV2.spanNormalizer.number.special;

import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import org.json.JSONObject;

public class RomanNumberNormalizer extends BaseNormalizer {

    public RomanNumberNormalizer(JSONObject config){
        super(config);
    }

    @Override
    public String doNorm(String spokenFormEntityString) {
        spokenFormEntityString = spokenFormEntityString.replace(" la mã", "");
        int num = Integer.parseInt(spokenFormEntityString);
        int[] val = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] syb = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        String roman_num = "";
        int i = 0;
        while (num > 0){
            while(num/val[i] > 0){
                roman_num += syb[i];
                num -= val[i];
            }
            i += 1;
        }
        return roman_num;
    }
}
