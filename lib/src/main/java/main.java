import VinSTTNorm.asrnormalizer.config.normalizerconfig.NormalizerConfig;
import VinSTTNorm.asrnormalizer.config.normalizerconfig.VoiceControlNormalizerConfig;
import VinSTTNormV2.config.OnlineConfig;
import VinSTTNormV2.modelOuputHandler.*;
import VinSTTNormV2.spanExtractor.BaseExtractor;
import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanExtractor.exotic.AbbreviationExtractor;
import VinSTTNormV2.spanExtractor.exotic.LexiconExtractor;
import VinSTTNormV2.spanExtractor.number.CommonNumberExtractor;
import VinSTTNormV2.spanExtractor.number.date.MonthExtractor;
import VinSTTNormV2.spanExtractor.number.date.YearExtractor;
import VinSTTNormV2.spanExtractor.number.time.TimeExtractor;
import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.AbbreviationNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.LexiconNormalizer;
import VinSTTNormV2.spanNormalizer.number.CommonNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.MonthNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.YearNormalizer;
import VinSTTNormV2.spanNormalizer.number.score.ScoreNormalizer;
import VinSTTNormV2.spanNormalizer.number.time.TimeNormalizer;
import VinSTTNormV2.utilities.Utilities;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.Hashtable;

public class main {
    public static void main(String[] args) {
//        String text1 = "hai mươi giờ";
//        VinFastVoiceControlAsrNormalizer normalizer = new VinFastVoiceControlAsrNormalizer();
//        System.out.println(String.format("Input: %s\nOutput: %s", text1, normalizer.normText(text1)));


        Hashtable<String, BaseHandler> handlers = new Hashtable<>();

        handlers.put("exotic", new ExoticHandler());
        handlers.put("link", new LinkHandler());
        handlers.put("number", new NumberHandler());
        handlers.put("date", new DateHandler());
        handlers.put("address", new AddressHandler());
        handlers.put("normal", new MixHandler());
        handlers.put("score", new ScoreHandler());


        String[] text = {"sao nờ tê a còng gờ meo chấm com"};
        String[] term = {"link"};

        for (int i = 0; i < text.length; i++){
            String normedText = handlers.get(term[i]).normAll(text[i]);
            System.out.println(normedText);
        }


    }

}
