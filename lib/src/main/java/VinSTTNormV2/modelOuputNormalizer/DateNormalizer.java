package VinSTTNormV2.modelOuputNormalizer;

import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanExtractor.number.FSTBillionNumberExtractor;
import VinSTTNormV2.spanExtractor.number.FSTMillionNumberExtractor;
import VinSTTNormV2.spanExtractor.number.FSTOneTwoThreeDigitExtractor;
import VinSTTNormV2.spanExtractor.number.FSTThousandNumberExtractor;
import VinSTTNormV2.spanExtractor.number.date.MonthExtractor;
import VinSTTNormV2.spanExtractor.number.date.MonthYearCountExtractor;
import VinSTTNormV2.spanExtractor.number.date.YearExtractor;
import VinSTTNormV2.spanNormalizer.number.FSTBillionNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.FSTMillionNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.FSTOneTwoThreeNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.FSTThousandNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.MonthNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.MonthYearCountNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.YearNormalizer;
import VinSTTNormV2.utilities.Utilities;

import java.util.Locale;

public class DateNormalizer extends BaseOutputNormalizer {
    public ExtractorAndNorm[] terms;
    public DateNormalizer(){
        super();
        this.terms = new ExtractorAndNorm[]{
                new ExtractorAndNorm(new MonthExtractor(config), new MonthNormalizer(config)),
                new ExtractorAndNorm(new YearExtractor(config), new YearNormalizer(config)),
                new ExtractorAndNorm(new MonthYearCountExtractor(config), new MonthYearCountNormalizer(config)),

                new ExtractorAndNorm(new FSTOneTwoThreeDigitExtractor(config), new FSTOneTwoThreeNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTThousandNumberExtractor(config), new FSTThousandNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTMillionNumberExtractor(config), new FSTMillionNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTBillionNumberExtractor(config), new FSTBillionNumberNormalizer(config)),
        };
    }

    @Override
    public String normAll(String text) {
        text = text.replaceAll(" +", " ");
        text = text.trim();
        text = "<s> " + text.toLowerCase(Locale.ROOT) + " </s>";
        for (ExtractorAndNorm term: this.terms){
            SpanObject[] spans = term.extractor.getSpans(text);
            for(SpanObject span : spans){
                System.out.println(span.toString());
            }
            term.normalizer.doAllNorm(spans);
            text = Utilities.replaceString(text, spans);
        }
        text = text.substring(4, text.length() - 5);
        return text;
    }
}
