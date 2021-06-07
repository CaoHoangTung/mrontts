package VinSTTNormV2.modelOuputHandler;


import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanExtractor.number.FSTOneTwoThreeDigitExtractor;
import VinSTTNormV2.spanExtractor.number.FSTThousandNumberExtractor;
import VinSTTNormV2.spanNormalizer.number.FSTOneTwoThreeNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.FSTThousandNumberNormalizer;
import VinSTTNormV2.spanNormalizer.number.score.ScoreNormalizer;
import VinSTTNormV2.utilities.Utilities;

public class ScoreHandler extends BaseHandler{

    public ExtractorAndNorm[] terms;
    public ScoreHandler(){
        super();
        this.terms = new ExtractorAndNorm[]{
                new ExtractorAndNorm(new FSTOneTwoThreeDigitExtractor(config), new FSTOneTwoThreeNumberNormalizer(config)),
                new ExtractorAndNorm(new FSTThousandNumberExtractor(config), new FSTThousandNumberNormalizer(config)),
        };

        this.normalizers.add(new ScoreNormalizer(config));
    }



    @Override
    public String normAll(String text) {
        text  = super.normAll(text);
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
        return text.replaceAll(" ", "-");
    }
}
