package VinSTTNormV2.online;


import VinSTTNormV2.taggers.SpanObject;
import VinSTTNormV2.taggers.number.FSTOneTwoThreeDigitTagger;
import VinSTTNormV2.taggers.number.FSTThousandNumberTagger;
import VinSTTNormV2.verbalizers.number.FSTOneTwoThreeNumberVerbalizer;
import VinSTTNormV2.verbalizers.number.FSTThousandNumberVerbalizer;
import VinSTTNormV2.utilities.Utilities;
import VinSTTNormV2.verbalizers.number.score.ScoreVerbalizer;

public class Score extends BaseOutputNormalizer {

    public TaggerAndVerbalizer[] terms;
    public Score(){
        super();
        this.terms = new TaggerAndVerbalizer[]{
                new TaggerAndVerbalizer(new FSTOneTwoThreeDigitTagger(config), new FSTOneTwoThreeNumberVerbalizer(config)),
                new TaggerAndVerbalizer(new FSTThousandNumberTagger(config), new FSTThousandNumberVerbalizer(config)),
        };

        this.normalizers.add(new ScoreVerbalizer(config));
    }



    @Override
    public String normAll(String text) {
        text  = super.normAll(text);
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
        return text.replaceAll(" ", "-");
    }
}
