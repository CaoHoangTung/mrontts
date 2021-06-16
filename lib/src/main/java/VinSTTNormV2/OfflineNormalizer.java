package VinSTTNormV2;

import VinSTTNormV2.config.OfflineConfig;
import VinSTTNormV2.config.normalizerconfig.BaseNormalizerConfig;
import VinSTTNormV2.config.normalizerconfig.VinFastVoiceControlNormalizerConfig;
import VinSTTNormV2.spanExtractor.BaseExtractor;
import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanExtractor.exotic.AbbreviationExtractor;
import VinSTTNormV2.spanExtractor.exotic.CharacterLexiconExtractor;
import VinSTTNormV2.spanExtractor.exotic.LexiconExtractor;
import VinSTTNormV2.spanExtractor.exotic.SegmentExtractor;
import VinSTTNormV2.spanExtractor.link.WebsiteExtractor;
import VinSTTNormV2.spanExtractor.number.*;
import VinSTTNormV2.spanExtractor.number.calculations.SimpleCalculationExtractor;
import VinSTTNormV2.spanExtractor.number.calculations.SqrtCalculationExtractor;
import VinSTTNormV2.spanExtractor.number.date.MonthExtractor;
import VinSTTNormV2.spanExtractor.number.date.MonthYearCountExtractor;
import VinSTTNormV2.spanExtractor.number.date.YearExtractor;
import VinSTTNormV2.spanExtractor.number.special.NumberPunctuationExtractor;
import VinSTTNormV2.spanExtractor.number.special.RomanNumberExtractor;
import VinSTTNormV2.spanExtractor.number.special.UnitExtractor;
import VinSTTNormV2.spanExtractor.number.time.TimeExtractor;
import VinSTTNormV2.spanExtractor.propername.SameDictNameExtractor;
import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.AbbreviationNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.CharacterLexiconNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.LexiconNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.SegmentNormalizer;
import VinSTTNormV2.spanNormalizer.link.WebNormalizer;
import VinSTTNormV2.spanNormalizer.number.*;
import VinSTTNormV2.spanNormalizer.number.calculations.SimpleCalculationNormalizer;
import VinSTTNormV2.spanNormalizer.number.calculations.SqrtCalculationNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.MonthNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.MonthYearCountNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.YearNormalizer;
import VinSTTNormV2.spanNormalizer.number.special.NumberPunctuationNormalizer;
import VinSTTNormV2.spanNormalizer.number.special.RomanNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.special.UnitNormalizer;
import VinSTTNormV2.spanNormalizer.number.time.TimeNormalizer;
import VinSTTNormV2.spanNormalizer.propername.SameDictNameNormalizer;
import VinSTTNormV2.utilities.Utilities;
import org.json.JSONObject;

/**
 * The main class of the package. Use to normalize text
 */
public class OfflineNormalizer {

    class ExtractorAndNorm{
        public BaseExtractor extractor;
        public BaseNormalizer normalizer;

        public ExtractorAndNorm(BaseExtractor extractor, BaseNormalizer normalizer){
            this.extractor = extractor;
            this.normalizer = normalizer;
        }
    }

    ExtractorAndNorm[] terms;

    public OfflineNormalizer() {
        BaseNormalizerConfig offlineConfig = new OfflineConfig();
        JSONObject config = offlineConfig.getConfig();
        this.terms = new ExtractorAndNorm[]{
                new ExtractorAndNorm(new LexiconExtractor(config), new LexiconNormalizer(config)),

                new ExtractorAndNorm(new SameDictNameExtractor(config), new SameDictNameNormalizer(config)),

                new ExtractorAndNorm(new FSTSerialNumberExtractor(config), new FSTSerialNumberNormalizer(config)),

                new ExtractorAndNorm(new MonthExtractor(config), new MonthNormalizer(config)),
                new ExtractorAndNorm(new YearExtractor(config), new YearNormalizer(config)),
                new ExtractorAndNorm(new MonthYearCountExtractor(config), new MonthYearCountNormalizer(config)),

                new ExtractorAndNorm(new FSTOneTwoThreeDigitExtractor(config), new FSTOneTwoThreeNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTThousandNumberExtractor(config), new FSTThousandNumberNormalizer(config)),
//                new ExtractorAndNorm(new FSTMillionNumberExtractor(config), new FSTMillionNumberNormalizer(config)),
//                new ExtractorAndNorm(new FSTBillionNumberExtractor(config), new FSTBillionNumberNormalizer(config)),

                new ExtractorAndNorm(new RomanNumberExtractor(config), new RomanNumberNormalizer(config)),

                new ExtractorAndNorm(new NumberPunctuationExtractor(config), new NumberPunctuationNormalizer(config)),
                new ExtractorAndNorm(new TimeExtractor(config), new TimeNormalizer(config)),
                new ExtractorAndNorm(new SqrtCalculationExtractor(config), new SqrtCalculationNormalizer(config)),
                new ExtractorAndNorm(new SimpleCalculationExtractor(config), new SimpleCalculationNormalizer(config)),


                new ExtractorAndNorm(new CharacterLexiconExtractor(config), new CharacterLexiconNormalizer(config)),
                new ExtractorAndNorm(new AbbreviationExtractor(config), new AbbreviationNormalizer(config)),

                new ExtractorAndNorm(new SegmentExtractor(config), new SegmentNormalizer(config)),
                new ExtractorAndNorm(new UnitExtractor(config), new UnitNormalizer(config)),
                new ExtractorAndNorm(new WebsiteExtractor(config), new WebNormalizer(config))

        };
    }


    /**
     * Norm text.
     * Usage: result = Normalizer.normText(yourText)
     */
    public String normText(String text) {
        text = text.replaceAll(" +", " ");
        text = text.trim();

        text = "<s> " + text + " </s>";

        for (ExtractorAndNorm term: this.terms){
            SpanObject[] spans = term.extractor.getSpans(text);
            term.normalizer.doAllNorm(spans);
            for(SpanObject span : spans){
                System.out.println(span.toString());
            }
            text = Utilities.replaceString(text, spans);
        }

        text = text.substring(4, text.length() - 5);

        return text.replace("  ", " ");
    }
}
