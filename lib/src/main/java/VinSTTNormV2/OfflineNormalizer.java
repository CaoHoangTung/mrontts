package VinSTTNormV2;

import VinSTTNormV2.config.NormalizerConfig;
import VinSTTNormV2.config.OfflineConfig;
import VinSTTNormV2.taggers.BaseTagger;
import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.taggers.exotic.AbbreviationTagger;
import VinSTTNormV2.taggers.exotic.CharacterLexiconTagger;
import VinSTTNormV2.taggers.exotic.LexiconTagger;
import VinSTTNormV2.taggers.exotic.SegmentTagger;
import VinSTTNormV2.taggers.link.WebsiteTagger;
import VinSTTNormV2.taggers.number.*;
import VinSTTNormV2.taggers.number.calculations.SimpleCalculationTagger;
import VinSTTNormV2.taggers.number.calculations.SqrtCalculationTagger;
import VinSTTNormV2.taggers.number.date.MonthTagger;
import VinSTTNormV2.taggers.number.date.YearTagger;
import VinSTTNormV2.taggers.number.special.NumberPunctuationTagger;
import VinSTTNormV2.taggers.number.special.RomanNumberTagger;
import VinSTTNormV2.taggers.number.special.UnitTagger;
import VinSTTNormV2.taggers.number.time.TimeTagger;
import VinSTTNormV2.taggers.propername.PersonNameTagger;
import VinSTTNormV2.taggers.propername.SameDictNameTagger;
import VinSTTNormV2.verbalizers.BaseVerbalizer;
import VinSTTNormV2.verbalizers.exotic.AbbreviationVerbalizer;
import VinSTTNormV2.verbalizers.exotic.CharacterLexiconVerbalizer;
import VinSTTNormV2.verbalizers.exotic.LexiconVerbalizer;
import VinSTTNormV2.verbalizers.exotic.SegmentVerbalizer;
import VinSTTNormV2.verbalizers.link.WebVerbalizer;
import VinSTTNormV2.verbalizers.number.*;
import VinSTTNormV2.verbalizers.number.calculations.SimpleCalculationVerbalizer;
import VinSTTNormV2.verbalizers.number.calculations.SqrtCalculationVerbalizer;
import VinSTTNormV2.verbalizers.number.date.MonthVerbalizer;
import VinSTTNormV2.verbalizers.number.date.YearVerbalizer;
import VinSTTNormV2.verbalizers.number.special.NumberPunctuationVerbalizer;
import VinSTTNormV2.verbalizers.number.special.RomanNumberVerbalizer;
import VinSTTNormV2.verbalizers.number.special.UnitVerbalizer;
import VinSTTNormV2.verbalizers.number.time.TimeVerbalizer;
import VinSTTNormV2.verbalizers.propername.PersonNameVerbalizer;
import VinSTTNormV2.verbalizers.propername.SameDictNameVerbalizer;
import VinSTTNormV2.utilities.Utilities;
import org.json.JSONObject;

/**
 * The main class of the package. Use to normalize text
 */
public class OfflineNormalizer {

    class ExtractorAndNorm{
        public BaseTagger extractor;
        public BaseVerbalizer normalizer;

        public ExtractorAndNorm(BaseTagger extractor, BaseVerbalizer normalizer){
            this.extractor = extractor;
            this.normalizer = normalizer;
        }
    }

    ExtractorAndNorm[] terms;

    public OfflineNormalizer() {
        NormalizerConfig offlineConfig = new OfflineConfig();
        JSONObject config = offlineConfig.getConfig();
        this.terms = new ExtractorAndNorm[]{
                new ExtractorAndNorm(new LexiconTagger(config), new LexiconVerbalizer(config)),

                new ExtractorAndNorm(new PersonNameTagger(config), new PersonNameVerbalizer(config)),
//                new ExtractorAndNorm(new SpecialFullNameExtractor(config), new SpecialFullNameNormalizer(config)),
                new ExtractorAndNorm(new SameDictNameTagger(config), new SameDictNameVerbalizer(config)),

                new ExtractorAndNorm(new FSTSerialNumberTagger(config), new FSTSerialNumberVerbalizer(config)),

                new ExtractorAndNorm(new MonthTagger(config), new MonthVerbalizer(config)),
                new ExtractorAndNorm(new YearTagger(config), new YearVerbalizer(config)),
//                new ExtractorAndNorm(new MonthYearCountExtractor(config), new MonthYearCountNormalizer(config)),

                new ExtractorAndNorm(new FSTOneTwoThreeDigitTagger(config), new FSTOneTwoThreeNumberVerbalizer(config)),
                new ExtractorAndNorm(new FSTThousandNumberTagger(config), new FSTThousandNumberVerbalizer(config)),
                new ExtractorAndNorm(new FSTMillionNumberTagger(config), new FSTMillionNumberVerbalizer(config)),
                new ExtractorAndNorm(new FSTBillionNumberTagger(config), new FSTBillionNumberVerbalizer(config)),

                new ExtractorAndNorm(new RomanNumberTagger(config), new RomanNumberVerbalizer(config)),

                new ExtractorAndNorm(new CharacterLexiconTagger(config), new CharacterLexiconVerbalizer(config)),
                new ExtractorAndNorm(new AbbreviationTagger(config), new AbbreviationVerbalizer(config)),

                new ExtractorAndNorm(new NumberPunctuationTagger(config), new NumberPunctuationVerbalizer(config)),
                new ExtractorAndNorm(new TimeTagger(config), new TimeVerbalizer(config)),
                new ExtractorAndNorm(new SqrtCalculationTagger(config), new SqrtCalculationVerbalizer(config)),
                new ExtractorAndNorm(new SimpleCalculationTagger(config), new SimpleCalculationVerbalizer(config)),

                new ExtractorAndNorm(new SegmentTagger(config), new SegmentVerbalizer(config)),
                new ExtractorAndNorm(new UnitTagger(config), new UnitVerbalizer(config)),
                new ExtractorAndNorm(new WebsiteTagger(config), new WebVerbalizer(config))

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
//            for(SpanObject span : spans){
//                System.out.println(span.toString());
//            }
            text = Utilities.replaceString(text, spans);
        }

        text = text.substring(4, text.length() - 5);

        return text.replace("  ", " ");
    }
}
