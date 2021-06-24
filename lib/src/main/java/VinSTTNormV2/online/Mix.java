package VinSTTNormV2.online;

import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.taggers.exotic.SegmentTagger;
import VinSTTNormV2.taggers.number.FSTBillionNumberTagger;
import VinSTTNormV2.taggers.number.FSTMillionNumberTagger;
import VinSTTNormV2.taggers.number.FSTOneTwoThreeDigitTagger;
import VinSTTNormV2.taggers.number.FSTThousandNumberTagger;
import VinSTTNormV2.taggers.number.special.NumberPunctuationTagger;
import VinSTTNormV2.taggers.number.special.UnitTagger;
import VinSTTNormV2.verbalizers.exotic.SegmentVerbalizer;
import VinSTTNormV2.verbalizers.number.FSTBillionNumberVerbalizer;
import VinSTTNormV2.verbalizers.number.FSTMillionNumberVerbalizer;
import VinSTTNormV2.verbalizers.number.FSTOneTwoThreeNumberVerbalizer;
import VinSTTNormV2.verbalizers.number.FSTThousandNumberVerbalizer;
import VinSTTNormV2.verbalizers.number.special.NumberPunctuationVerbalizer;
import VinSTTNormV2.verbalizers.number.special.UnitVerbalizer;
import VinSTTNormV2.utilities.Utilities;

import java.util.Locale;

public class Mix extends BaseOutputNormalizer {

    public TaggerAndVerbalizer[] terms;
    public Mix(){
        super();
        this.terms = new TaggerAndVerbalizer[]{
                new TaggerAndVerbalizer(new FSTOneTwoThreeDigitTagger(config), new FSTOneTwoThreeNumberVerbalizer(config)),
                new TaggerAndVerbalizer(new FSTThousandNumberTagger(config), new FSTThousandNumberVerbalizer(config)),
                new TaggerAndVerbalizer(new FSTMillionNumberTagger(config), new FSTMillionNumberVerbalizer(config)),
                new TaggerAndVerbalizer(new FSTBillionNumberTagger(config), new FSTBillionNumberVerbalizer(config)),

                new TaggerAndVerbalizer(new NumberPunctuationTagger(config), new NumberPunctuationVerbalizer(config)),
                new TaggerAndVerbalizer(new UnitTagger(config), new UnitVerbalizer(config)),

                new TaggerAndVerbalizer(new SegmentTagger(config), new SegmentVerbalizer(config))

//                new ExtractorAndNorm(new NumLetSeqExtractor(config), new NumLetSeqNormalizer(config))
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
        for (TaggerAndVerbalizer term: this.terms){
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
