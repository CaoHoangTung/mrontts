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
        NormalizerConfig vinFastVoiceControlNormalizerConfig = new VinFastVoiceControlNormalizerConfig();
        JSONObject config = vinFastVoiceControlNormalizerConfig.getConfig();

        String text = "ngày mười chín tháng năm năm hai không hai mốt và một triệu bốn trăm ba mươi tám ngàn ba trăm hai chín";
        ExtractorAndNorm[] terms = new ExtractorAndNorm[]{
                new ExtractorAndNorm(new LexiconExtractor(config), new LexiconNormalizer(config)),
                new ExtractorAndNorm(new AbbreviationExtractor(config), new AbbreviationNormalizer(config)),
                new ExtractorAndNorm(new MonthExtractor(config), new MonthNormalizer(config)),
                new ExtractorAndNorm(new YearExtractor(config), new YearNormalizer(config)),
                new ExtractorAndNorm(new TimeExtractor(config), new TimeNormalizer(config)),
                new ExtractorAndNorm(new FSTOneTwoThreeDigitExtractor(config), new FSTOneTwoThreeNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTThousandNumberExtractor(config), new FSTThousandNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTMillionNumberExtractor(config), new FSTMillionNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTBillionNumberExtractor(config), new FSTBillionNumberNormalizer(config))
        };

        for(ExtractorAndNorm term: terms){
            SpanObject[] spans = term.extractor.getSpans(text);
            term.normalizer.doAllNorm(spans);
            for(SpanObject span : spans){
                System.out.println(span.toString());
            }
            text = Utilities.replaceString(text, spans);
        }
        System.out.println(text);

//        String text2 = "bác hồ sinh năm một chín chín tám và đếm ba hai một";
//        FSTSerialNumberextractor numberExtractor = new FSTSerialNumberextractor(config);
//        SpanObject[] spans = numberExtractor.getSpans(text2);
//        for(SpanObject span : spans){
//            System.out.println(span.toString());
//        }
//        VinFastVoiceControlAsrNormalizer myNormalizer = new VinFastVoiceControlAsrNormalizer();
////        String text = "tăng âm lượng thêm năm mươi phần trăm";
//        String output = myNormalizer.normText(text);
//        System.out.println(output);
    }
}
