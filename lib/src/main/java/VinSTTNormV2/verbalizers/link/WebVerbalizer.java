package VinSTTNormV2.verbalizers.link;

import VinSTTNormV2.verbalizers.NameMapVerbalizer;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class WebVerbalizer extends NameMapVerbalizer {
    public final String TAG = "web_normalizer";

    public WebVerbalizer(JSONObject config){
        super(config);
    }

    private static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    @Override
    public String getType(){
        return "website_name";
    }

    @Override
    public String doNorm(String spokenFormEntityString){
        spokenFormEntityString = spokenFormEntityString.toLowerCase(Locale.ROOT);
        String result = super.doNorm(spokenFormEntityString);

        if (result == null || result.isEmpty() || result.equals(spokenFormEntityString)){
            spokenFormEntityString = spokenFormEntityString.replace("hai chấm", ":");
            spokenFormEntityString = spokenFormEntityString.replace("chấm", ".");
            spokenFormEntityString = spokenFormEntityString.replace("gạch chéo", "/");
            spokenFormEntityString = spokenFormEntityString.replace("a còng", "@");
            spokenFormEntityString = spokenFormEntityString.replace("g mêu", "gmail");
            spokenFormEntityString = spokenFormEntityString.replace("g meo", "gmail");

            String noredString = deAccent(spokenFormEntityString).replace("đ", "d");
            return noredString.replace(" ", "");
        }
        else{
            return result;
        }
    }
}
