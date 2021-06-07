package VinSTTNormV2.modelOuputHandler;

import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanExtractor.exotic.CharacterLexiconExtractor;
import VinSTTNormV2.spanExtractor.exotic.SegmentExtractor;
import VinSTTNormV2.spanExtractor.number.*;
import VinSTTNormV2.spanExtractor.number.special.NumberPunctuationExtractor;
import VinSTTNormV2.spanNormalizer.exotic.CharacterLexiconNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.SegmentNormalizer;
import VinSTTNormV2.spanNormalizer.number.*;
import VinSTTNormV2.spanNormalizer.number.special.NumberPunctuationNormalizer;
import VinSTTNormV2.utilities.Utilities;


public class AddressHandler extends BaseHandler{

    public AddressHandler(){
        super();
        this.terms = new ExtractorAndNorm[]{
                new ExtractorAndNorm(new FSTSerialNumberExtractor(config), new FSTSerialNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTOneTwoThreeDigitExtractor(config), new FSTOneTwoThreeNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTThousandNumberExtractor(config), new FSTThousandNumberNormalizer(config)),
                new ExtractorAndNorm(new NumberPunctuationExtractor(config), new NumberPunctuationNormalizer(config)),
                new ExtractorAndNorm(new CharacterLexiconExtractor(config), new CharacterLexiconNormalizer(config)),
                new ExtractorAndNorm(new SegmentExtractor(config), new SegmentNormalizer(config))

        };

//        this.normalizers.add(new CharacterLexiconNormalizer(config));/**/

    }

    @Override
    public String normAll(String text) {
        text = text.replaceAll(" +", " ");
        text = text.trim();
        text = "<s> " + text + " </s>";
        for (ExtractorAndNorm term: this.terms){
            SpanObject[] spans = term.extractor.getSpans(text);
            for(SpanObject span : spans){
                System.out.println(span.toString());
            }
            term.normalizer.doAllNorm(spans);
            text = Utilities.replaceString(text, spans);
        }
        text = text.substring(4, text.length() - 5);

        text = super.normAll(text);
        return text;
    }
}
