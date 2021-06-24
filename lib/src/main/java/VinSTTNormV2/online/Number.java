package VinSTTNormV2.online;

import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.taggers.number.*;
import VinSTTNormV2.taggers.number.calculations.SimpleCalculationTagger;
import VinSTTNormV2.taggers.number.calculations.SqrtCalculationTagger;
import VinSTTNormV2.taggers.number.date.MonthYearCountTagger;
import VinSTTNormV2.taggers.number.special.NumberPunctuationTagger;
import VinSTTNormV2.taggers.number.special.RomanNumberTagger;
import VinSTTNormV2.taggers.number.special.UnitTagger;
import VinSTTNormV2.taggers.number.time.TimeTagger;
import VinSTTNormV2.verbalizers.number.*;
import VinSTTNormV2.verbalizers.number.calculations.SimpleCalculationVerbalizer;
import VinSTTNormV2.verbalizers.number.calculations.SqrtCalculationVerbalizer;
import VinSTTNormV2.verbalizers.number.date.MonthYearCountVerbalizer;
import VinSTTNormV2.verbalizers.number.special.NumberPunctuationVerbalizer;
import VinSTTNormV2.verbalizers.number.special.RomanNumberVerbalizer;
import VinSTTNormV2.verbalizers.number.special.UnitVerbalizer;
import VinSTTNormV2.verbalizers.number.time.TimeVerbalizer;
import VinSTTNormV2.utilities.Utilities;

import java.util.Locale;

public class Number extends BaseOutputNormalizer {

    public TaggerAndVerbalizer[] terms;
    public Number(){
        super();
        this.terms = new TaggerAndVerbalizer[]{
                new TaggerAndVerbalizer(new FSTSerialNumberTagger(config), new FSTSerialNumberVerbalizer(config)),
                new TaggerAndVerbalizer(new MonthYearCountTagger(config), new MonthYearCountVerbalizer(config)),

                new TaggerAndVerbalizer(new FSTOneTwoThreeDigitTagger(config), new FSTOneTwoThreeNumberVerbalizer(config)),

                new TaggerAndVerbalizer(new UnitTagger(config), new UnitVerbalizer(config)),

                new TaggerAndVerbalizer(new FSTThousandNumberTagger(config), new FSTThousandNumberVerbalizer(config)),
                new TaggerAndVerbalizer(new FSTMillionNumberTagger(config), new FSTMillionNumberVerbalizer(config)),
                new TaggerAndVerbalizer(new FSTBillionNumberTagger(config), new FSTBillionNumberVerbalizer(config)),

                new TaggerAndVerbalizer(new SqrtCalculationTagger(config), new SqrtCalculationVerbalizer(config)),
                new TaggerAndVerbalizer(new SimpleCalculationTagger(config), new SimpleCalculationVerbalizer(config)),

                new TaggerAndVerbalizer(new RomanNumberTagger(config), new RomanNumberVerbalizer(config)),

                new TaggerAndVerbalizer(new NumberPunctuationTagger(config), new NumberPunctuationVerbalizer(config)),
                new TaggerAndVerbalizer(new TimeTagger(config), new TimeVerbalizer(config)),


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

        for (TaggerAndVerbalizer term: this.terms){
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
