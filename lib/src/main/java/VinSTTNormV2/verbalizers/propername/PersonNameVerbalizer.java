package VinSTTNormV2.verbalizers.propername;

import VinSTTNormV2.verbalizers.BaseVerbalizer;
import org.json.JSONObject;

public class PersonNameVerbalizer extends BaseVerbalizer {
    protected final String TAG = "person_name_normalizer";

    public PersonNameVerbalizer(JSONObject config){
        super(config);
    }

    @Override
    public String doNorm(String spokenFormEntityString) {
        String[] tokens = spokenFormEntityString.split(" ");
        StringBuilder result = new StringBuilder();

        for (int idx = 0 ; idx < tokens.length-1 ; idx++) {
            String token = tokens[idx];
            result.append(Character.toUpperCase(token.charAt(0))).append(token.substring(1));
            result.append(' ');
        }
        if (tokens.length > 0)
            result.append(Character.toUpperCase(tokens[tokens.length-1].charAt(0))).append(tokens[tokens.length-1].substring(1));

        return result.toString();
    }
}
