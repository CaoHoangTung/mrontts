import VinSTTNorm.asrnormalizer.config.normalizerconfig.NormalizerConfig;
import VinSTTNorm.asrnormalizer.config.normalizerconfig.VinFastVoiceControlNormalizerConfig;
import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanExtractor.exotic.AbbreviationExtractor;
import VinSTTNormV2.spanExtractor.exotic.LexiconExtractor;
import VinSTTNormV2.spanExtractor.number.*;
import VinSTTNormV2.spanExtractor.number.date.MonthExtractor;
import VinSTTNormV2.spanExtractor.number.date.YearExtractor;
import VinSTTNormV2.spanExtractor.number.time.TimeExtractor;
import VinSTTNormV2.spanNormalizer.exotic.AbbreviationNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.LexiconNormalizer;
import VinSTTNormV2.spanNormalizer.number.*;
import VinSTTNormV2.spanNormalizer.number.date.MonthNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.YearNormalizer;
import VinSTTNormV2.spanNormalizer.number.time.TimeNormalizer;
import VinSTTNormV2.utilities.Utilities;
import org.json.JSONObject;

public class main {
    public static void main(String[] args) {
        String text = "một triệu bốn trăm ba mươi tám ngàn ba trăm hai chín";

        VinFastVoiceControlAsrNormalizer normalizer = new VinFastVoiceControlAsrNormalizer();


        System.out.println(String.format("Input: %s\nOutput: %s", text, normalizer.normText(text)));
    }
}
