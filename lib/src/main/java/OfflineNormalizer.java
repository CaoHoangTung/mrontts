import VinSTTNormV2.config.normalizerconfig.BaseNormalizerConfig;
import VinSTTNormV2.config.normalizerconfig.VinFastVoiceControlNormalizerConfig;
import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanExtractor.exotic.AbbreviationExtractor;
import VinSTTNormV2.spanExtractor.exotic.LexiconExtractor;
import VinSTTNormV2.spanExtractor.number.FSTBillionNumberExtractor;
import VinSTTNormV2.spanExtractor.number.FSTMillionNumberExtractor;
import VinSTTNormV2.spanExtractor.number.FSTOneTwoThreeDigitExtractor;
import VinSTTNormV2.spanExtractor.number.FSTThousandNumberExtractor;
import VinSTTNormV2.spanExtractor.number.date.MonthExtractor;
import VinSTTNormV2.spanExtractor.number.date.YearExtractor;
import VinSTTNormV2.spanExtractor.number.special.UnitExtractor;
import VinSTTNormV2.spanExtractor.number.time.TimeExtractor;
import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.AbbreviationNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.LexiconNormalizer;
import VinSTTNormV2.spanNormalizer.number.FSTBillionNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.FSTMillionNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.FSTOneTwoThreeNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.FSTThousandNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.MonthNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.YearNormalizer;
import VinSTTNormV2.spanNormalizer.number.special.UnitNormalizer;
import VinSTTNormV2.spanNormalizer.number.time.TimeNormalizer;
import VinSTTNormV2.utilities.Utilities;
import org.json.JSONObject;

/**
 * The main class of the package. Use to normalize text
 */
//public class OfflineNormalizer {
//    ExtractorAndNorm[] terms;
//
//    OfflineNormalizer() {
//        BaseNormalizerConfig vinFastVoiceControlNormalizerConfig = new VinFastVoiceControlNormalizerConfig();
//        JSONObject config = vinFastVoiceControlNormalizerConfig.getConfig();
//        this.terms = new ExtractorAndNorm[]{
//                new ExtractorAndNorm(new LexiconExtractor(config), new LexiconNormalizer(config)),
//                new ExtractorAndNorm(new AbbreviationExtractor(config), new AbbreviationNormalizer(config)),
//                new ExtractorAndNorm(new MonthExtractor(config), new MonthNormalizer(config)),
//                new ExtractorAndNorm(new YearExtractor(config), new YearNormalizer(config)),
//
//                new ExtractorAndNorm(new FSTOneTwoThreeDigitExtractor(config), new FSTOneTwoThreeNumberNormalizer(config)),
//                new ExtractorAndNorm(new FSTThousandNumberExtractor(config), new FSTThousandNumberNormalizer(config)),
//
////                new ExtractorAndNorm(new UnitExtractor(config), new UnitNormalizer(config)),
//
//                new ExtractorAndNorm(new FSTMillionNumberExtractor(config), new FSTMillionNumberNormalizer(config)),
//                new ExtractorAndNorm(new FSTBillionNumberExtractor(config), new FSTBillionNumberNormalizer(config)),
//
//                new ExtractorAndNorm(new TimeExtractor(config), new TimeNormalizer(config)),
//        };
//    }
//
//
//    /**
//     * Norm text.
//     * Usage: result = Normalizer.normText(yourText)
//     */
//    public String normText(String text) {
//        text = text.replaceAll(" +", " ");
//        text = text.trim();
//
//        text = "<s> " + text + " </s>";
//
//        for (ExtractorAndNorm term: this.terms){
//            SpanObject[] spans = term.extractor.getSpans(text);
//            term.normalizer.doAllNorm(spans);
//            for(SpanObject span : spans){
//                System.out.println(span.toString());
//            }
//            text = Utilities.replaceString(text, spans);
//        }
//
//        text = text.substring(4, text.length() - 5);
//
//        return text;
//    }
//}
