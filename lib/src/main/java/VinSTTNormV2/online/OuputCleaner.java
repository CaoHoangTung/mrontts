package VinSTTNormV2.online;

import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.taggers.exotic.SegmentTagger;
import VinSTTNormV2.verbalizers.exotic.SegmentVerbalizer;
import VinSTTNormV2.utilities.Utilities;

public class OuputCleaner extends BaseOutputNormalizer {

    public OuputCleaner(){
        super();
        this.terms = new TaggerAndVerbalizer[]{
                new TaggerAndVerbalizer(new SegmentTagger(config), new SegmentVerbalizer(config))
        };
    }

    @Override
    public String normAll(String text) {
        text = text.replaceAll(" +", " ");
        text = text.trim();
        for (TaggerAndVerbalizer term: this.terms){
            SpanObject[] spans = term.extractor.getSpans(text);
            for(SpanObject span : spans){
                System.out.println(span.toString());
            }
            term.normalizer.doAllNorm(spans);
            text = Utilities.replaceString(text, spans);
        }

        return text;
    }
}
