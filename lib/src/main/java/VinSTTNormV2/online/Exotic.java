package VinSTTNormV2.online;


import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.taggers.exotic.SegmentTagger;
import VinSTTNormV2.verbalizers.exotic.AbbreviationVerbalizer;
import VinSTTNormV2.verbalizers.exotic.CharacterLexiconVerbalizer;
import VinSTTNormV2.verbalizers.exotic.LexiconVerbalizer;
import VinSTTNormV2.verbalizers.exotic.SegmentVerbalizer;
import VinSTTNormV2.utilities.Utilities;

import java.util.Locale;


public class Exotic extends BaseOutputNormalizer {

    public TaggerAndVerbalizer[] terms;
    public Exotic(){
        super();
        this.normalizers.add(new AbbreviationVerbalizer(config));
        this.normalizers.add(new LexiconVerbalizer(config));
        this.normalizers.add(new CharacterLexiconVerbalizer(config));
        this.normalizers.add(new SegmentVerbalizer(config));

        this.terms = new TaggerAndVerbalizer[]{
                new TaggerAndVerbalizer(new SegmentTagger(config), new SegmentVerbalizer(config))
        };
    }


    @Override
    public String normAll(String text) {
        text = super.normAll(text.toLowerCase(Locale.ROOT));

        text = text.replaceAll(" +", " ");
        text = text.trim();
        text = "<s> " + text + " </s>";
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
//    @Override
//    public String normAll(String spanText){
//        return super.normAll(spanText.toLowerCase(Locale.ROOT));
//    }
}
