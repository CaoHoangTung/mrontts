package VinSTTNormV2.modelOuputNormalizer;


import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanExtractor.exotic.SegmentExtractor;
import VinSTTNormV2.spanNormalizer.exotic.AbbreviationNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.CharacterLexiconNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.LexiconNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.SegmentNormalizer;
import VinSTTNormV2.utilities.Utilities;

import java.util.Locale;


public class ExoticNormalizer extends BaseOutputNormalizer {

    public ExtractorAndNorm[] terms;
    public ExoticNormalizer(){
        super();
        this.normalizers.add(new AbbreviationNormalizer(config));
        this.normalizers.add(new LexiconNormalizer(config));
        this.normalizers.add(new CharacterLexiconNormalizer(config));
        this.normalizers.add(new SegmentNormalizer(config));

        this.terms = new ExtractorAndNorm[]{
                new ExtractorAndNorm(new SegmentExtractor(config), new SegmentNormalizer(config))
        };
    }


    @Override
    public String normAll(String text) {
        text = super.normAll(text.toLowerCase(Locale.ROOT));

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


        return text;
    }
//    @Override
//    public String normAll(String spanText){
//        return super.normAll(spanText.toLowerCase(Locale.ROOT));
//    }
}
