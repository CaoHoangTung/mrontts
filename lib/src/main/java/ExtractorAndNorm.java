import VinSTTNormV2.spanExtractor.BaseExtractor;
import VinSTTNormV2.spanNormalizer.BaseNormalizer;

public class ExtractorAndNorm{
    public BaseExtractor extractor;
    public BaseNormalizer normalizer;

    public ExtractorAndNorm(BaseExtractor extractor, BaseNormalizer normalizer){
        this.extractor = extractor;
        this.normalizer = normalizer;
    }
}