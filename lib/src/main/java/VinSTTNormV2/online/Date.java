package VinSTTNormV2.online;

import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.taggers.number.FSTBillionNumberTagger;
import VinSTTNormV2.taggers.number.FSTMillionNumberTagger;
import VinSTTNormV2.taggers.number.FSTOneTwoThreeDigitTagger;
import VinSTTNormV2.taggers.number.FSTThousandNumberTagger;
import VinSTTNormV2.taggers.number.date.MonthTagger;
import VinSTTNormV2.taggers.number.date.MonthYearCountTagger;
import VinSTTNormV2.taggers.number.date.YearTagger;
import VinSTTNormV2.verbalizers.number.FSTBillionNumberVerbalizer;
import VinSTTNormV2.verbalizers.number.FSTMillionNumberVerbalizer;
import VinSTTNormV2.verbalizers.number.FSTOneTwoThreeNumberVerbalizer;
import VinSTTNormV2.verbalizers.number.FSTThousandNumberVerbalizer;
import VinSTTNormV2.verbalizers.number.date.MonthVerbalizer;
import VinSTTNormV2.verbalizers.number.date.MonthYearCountVerbalizer;
import VinSTTNormV2.verbalizers.number.date.YearVerbalizer;
import VinSTTNormV2.utilities.Utilities;

import java.util.Locale;

public class Date extends BaseOutputNormalizer {
    public TaggerAndVerbalizer[] terms;
    public Date(){
        super();
        this.terms = new TaggerAndVerbalizer[]{
                new TaggerAndVerbalizer(new MonthTagger(config), new MonthVerbalizer(config)),
                new TaggerAndVerbalizer(new YearTagger(config), new YearVerbalizer(config)),
                new TaggerAndVerbalizer(new MonthYearCountTagger(config), new MonthYearCountVerbalizer(config)),

                new TaggerAndVerbalizer(new FSTOneTwoThreeDigitTagger(config), new FSTOneTwoThreeNumberVerbalizer(config)),
                new TaggerAndVerbalizer(new FSTThousandNumberTagger(config), new FSTThousandNumberVerbalizer(config)),
                new TaggerAndVerbalizer(new FSTMillionNumberTagger(config), new FSTMillionNumberVerbalizer(config)),
                new TaggerAndVerbalizer(new FSTBillionNumberTagger(config), new FSTBillionNumberVerbalizer(config)),
        };
    }

    @Override
    public String normAll(String text) {
        text = text.replaceAll(" +", " ");
        text = text.trim();
        text = "<s> " + text.toLowerCase(Locale.ROOT) + " </s>";
        for (TaggerAndVerbalizer term: this.terms){
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
