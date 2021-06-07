package VinSTTNormV2.modelOuputHandler;


import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.CharacterLexiconNormalizer;
import VinSTTNormV2.spanNormalizer.link.WebNormalizer;
import VinSTTNormV2.utilities.Utilities;

import java.util.Locale;

public class LinkHandler extends BaseHandler{

    public ExtractorAndNorm[] terms;

    public LinkHandler(){
        super();
//        this.terms = new ExtractorAndNorm[]{
//                new ExtractorAndNorm(new CharacterLexiconExtractor(config), new CharacterLexiconNormalizer(config)),
//        };

        this.normalizers.add(new CharacterLexiconNormalizer(config));
        this.normalizers.add(new WebNormalizer(config));
    }

    @Override
    public String normAll(String spanText) {
//        text = text.replaceAll(" +", " ");
//        text = text.trim();
////        text = "<s> " + text + " </s>";
//        String textOriginal = text;
//        text = text.toLowerCase(Locale.ROOT);
//        for (ExtractorAndNorm term: this.terms){
//            SpanObject[] spans = term.extractor.getSpans(text);
//            for(SpanObject span : spans){
//                System.out.println(span.toString());
//            }
//            term.normalizer.doAllNorm(spans);
//            text = Utilities.replaceString(text, spans);
//            textOriginal = Utilities.replaceString(textOriginal, spans);
//        }
////        text = text.substring(4, text.length() - 5);
////        textOriginal = textOriginal.substring(4, text.length() - 5);

        boolean normed = false;
        for(BaseNormalizer normalizer: normalizers){
            SpanObject[] spans = new SpanObject[]{
                    new SpanObject(0, spanText.length()-1, "", spanText.toLowerCase(Locale.ROOT))
            };
            normalizer.doAllNorm(spans);
            for(SpanObject span : spans){
                System.out.println(span.toString());
                if (!span.text.equals(span.replacement)) normed = true;
            }
            if (normed){
                spanText = Utilities.replaceString(spanText, spans);
            }
        }
        return spanText;
    }


}
