package VinSTTNormV2.modelOuputNormalizer;

import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanExtractor.exotic.SegmentExtractor;
import VinSTTNormV2.spanNormalizer.exotic.SegmentNormalizer;
import VinSTTNormV2.utilities.Utilities;

public class OuputCleaner extends BaseOutputNormalizer {

    public OuputCleaner(){
        super();
        this.terms = new ExtractorAndNorm[]{
                new ExtractorAndNorm(new SegmentExtractor(config), new SegmentNormalizer(config))
        };
    }

    @Override
    public String normAll(String text) {
        text = text.replaceAll(" +", " ");
        text = text.trim();
        for (ExtractorAndNorm term: this.terms){
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
