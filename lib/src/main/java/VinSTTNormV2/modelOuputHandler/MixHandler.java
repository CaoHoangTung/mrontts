package VinSTTNormV2.modelOuputHandler;

import VinSTTNormV2.spanExtractor.BaseExtractor;
import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanExtractor.exotic.CharacterLexiconExtractor;
import VinSTTNormV2.spanExtractor.exotic.SegmentExtractor;
import VinSTTNormV2.spanExtractor.number.FSTBillionNumberExtractor;
import VinSTTNormV2.spanExtractor.number.FSTMillionNumberExtractor;
import VinSTTNormV2.spanExtractor.number.FSTOneTwoThreeDigitExtractor;
import VinSTTNormV2.spanExtractor.number.FSTThousandNumberExtractor;
import VinSTTNormV2.spanExtractor.number.date.MonthExtractor;
import VinSTTNormV2.spanExtractor.number.date.MonthYearCountExtractor;
import VinSTTNormV2.spanExtractor.number.date.YearExtractor;
import VinSTTNormV2.spanExtractor.number.special.NumberPunctuationExtractor;
import VinSTTNormV2.spanExtractor.number.special.UnitExtractor;
import VinSTTNormV2.spanExtractor.number.time.TimeExtractor;
import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.CharacterLexiconNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.SegmentNormalizer;
import VinSTTNormV2.spanNormalizer.number.FSTBillionNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.FSTMillionNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.FSTOneTwoThreeNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.FSTThousandNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.MonthNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.MonthYearCountNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.YearNormalizer;
import VinSTTNormV2.spanNormalizer.number.special.NumberPunctuationNormalizer;
import VinSTTNormV2.spanNormalizer.number.special.UnitNormalizer;
import VinSTTNormV2.spanNormalizer.number.time.TimeNormalizer;
import VinSTTNormV2.utilities.Utilities;

import java.util.Locale;

public class MixHandler extends BaseHandler{

    public ExtractorAndNorm[] terms;
    public MixHandler(){
        super();
        this.terms = new ExtractorAndNorm[]{
                new ExtractorAndNorm(new FSTOneTwoThreeDigitExtractor(config), new FSTOneTwoThreeNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTThousandNumberExtractor(config), new FSTThousandNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTMillionNumberExtractor(config), new FSTMillionNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTBillionNumberExtractor(config), new FSTBillionNumberNormalizer(config)),

                new ExtractorAndNorm(new NumberPunctuationExtractor(config), new NumberPunctuationNormalizer(config)),
                new ExtractorAndNorm(new UnitExtractor(config), new UnitNormalizer(config)),

//                new ExtractorAndNorm(new CharacterLexiconExtractor(config), new CharacterLexiconNormalizer(config))
        };

//        normalizers.add(new CharacterLexiconNormalizer(config));
    }

    @Override
    public String normAll(String text) {

        text = text.replaceAll(" +", " ");
        text = text.trim();
        text = "<s> " + text + " </s>";
        String textOriginal = text;
        text = text.toLowerCase(Locale.ROOT);
        for (ExtractorAndNorm term: this.terms){
            SpanObject[] spans = term.extractor.getSpans(text);
            for(SpanObject span : spans){
                System.out.println(span.toString());
            }
            term.normalizer.doAllNorm(spans);
            text = Utilities.replaceString(text, spans);
            textOriginal = Utilities.replaceString(textOriginal, spans);
        }
        textOriginal = textOriginal.substring(4, text.length() - 5);
        if (text.matches("[0-9]+")){
            return text;
        }
        textOriginal = super.normAll(textOriginal);
        return textOriginal;
    }
}
