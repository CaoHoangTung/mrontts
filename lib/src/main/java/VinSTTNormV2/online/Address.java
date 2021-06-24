package VinSTTNormV2.online;

import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.taggers.exotic.CharacterLexiconTagger;
import VinSTTNormV2.taggers.exotic.SegmentTagger;
import VinSTTNormV2.taggers.number.*;
import VinSTTNormV2.taggers.number.special.NumberPunctuationTagger;
import VinSTTNormV2.verbalizers.exotic.CharacterLexiconVerbalizer;
import VinSTTNormV2.verbalizers.exotic.SegmentVerbalizer;
import VinSTTNormV2.verbalizers.number.*;
import VinSTTNormV2.verbalizers.number.special.NumberPunctuationVerbalizer;
import VinSTTNormV2.utilities.Utilities;

import java.util.Locale;


public class Address extends BaseOutputNormalizer {

    public Address(){
        super();
        this.terms = new TaggerAndVerbalizer[]{
                new TaggerAndVerbalizer(new FSTSerialNumberTagger(config), new FSTSerialNumberVerbalizer(config)),
                new TaggerAndVerbalizer(new FSTOneTwoThreeDigitTagger(config), new FSTOneTwoThreeNumberVerbalizer(config)),
                new TaggerAndVerbalizer(new FSTThousandNumberTagger(config), new FSTThousandNumberVerbalizer(config)),
                new TaggerAndVerbalizer(new NumberPunctuationTagger(config), new NumberPunctuationVerbalizer(config)),
                new TaggerAndVerbalizer(new CharacterLexiconTagger(config), new CharacterLexiconVerbalizer(config)),
                new TaggerAndVerbalizer(new SegmentTagger(config), new SegmentVerbalizer(config)),
//                new ExtractorAndNorm(new NumLetSeqExtractor(config), new NumLetSeqNormalizer(config))

        };

//        this.normalizers.add(new CharacterLexiconNormalizer(config));/**/

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

        text = super.normAll(text);
        return textOriginal;
    }
}
