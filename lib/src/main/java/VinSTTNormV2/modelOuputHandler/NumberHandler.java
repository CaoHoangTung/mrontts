package VinSTTNormV2.modelOuputHandler;

import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanExtractor.number.*;
import VinSTTNormV2.spanExtractor.number.date.MonthYearCountExtractor;
import VinSTTNormV2.spanExtractor.number.special.NumberPunctuationExtractor;
import VinSTTNormV2.spanExtractor.number.special.UnitExtractor;
import VinSTTNormV2.spanExtractor.number.time.TimeExtractor;
import VinSTTNormV2.spanNormalizer.number.*;
import VinSTTNormV2.spanNormalizer.number.date.MonthYearCountNormalizer;
import VinSTTNormV2.spanNormalizer.number.special.NumberPunctuationNormalizer;
import VinSTTNormV2.spanNormalizer.number.special.UnitNormalizer;
import VinSTTNormV2.spanNormalizer.number.time.TimeNormalizer;
import VinSTTNormV2.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NumberHandler extends BaseHandler{

    public ExtractorAndNorm[] terms;
    public NumberHandler(){
        super();
        this.terms = new ExtractorAndNorm[]{
                new ExtractorAndNorm(new FSTSerialNumberExtractor(config), new FSTSerialNumberNormalizer(config)),
                new ExtractorAndNorm(new MonthYearCountExtractor(config), new MonthYearCountNormalizer(config)),

                new ExtractorAndNorm(new FSTOneTwoThreeDigitExtractor(config), new FSTOneTwoThreeNumberNormalizer(config)),

                new ExtractorAndNorm(new UnitExtractor(config), new UnitNormalizer(config)),

                new ExtractorAndNorm(new FSTThousandNumberExtractor(config), new FSTThousandNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTMillionNumberExtractor(config), new FSTMillionNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTBillionNumberExtractor(config), new FSTBillionNumberNormalizer(config)),

                new ExtractorAndNorm(new NumberPunctuationExtractor(config), new NumberPunctuationNormalizer(config)),
                new ExtractorAndNorm(new TimeExtractor(config), new TimeNormalizer(config)),


        };

//        this.normalizers.add(new TimeNormalizer(config));
//        this.normalizers.add(new UnitNormalizer(config));
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
//        text = text.substring(4, text.length() - 5);
        textOriginal = textOriginal.substring(4, text.length() - 5);
//        if (text.matches("[0-9]+")){
//            return text;
//        }
//        text = super.normAll(text);
        return textOriginal;
    }
}
