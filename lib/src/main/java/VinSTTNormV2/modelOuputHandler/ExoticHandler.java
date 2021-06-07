package VinSTTNormV2.modelOuputHandler;


import VinSTTNormV2.spanNormalizer.exotic.AbbreviationNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.CharacterLexiconNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.LexiconNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.SegmentNormalizer;

import java.util.Locale;


public class ExoticHandler extends BaseHandler{

    public ExoticHandler(){
        super();
        this.normalizers.add(new AbbreviationNormalizer(config));
        this.normalizers.add(new LexiconNormalizer(config));
        this.normalizers.add(new CharacterLexiconNormalizer(config));
        this.normalizers.add(new SegmentNormalizer(config));
    }

    @Override
    public String normAll(String spanText){
        return super.normAll(spanText.toLowerCase(Locale.ROOT));
    }
}
