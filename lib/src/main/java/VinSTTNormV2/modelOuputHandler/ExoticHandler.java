package VinSTTNormV2.modelOuputHandler;

import VinSTTNorm.asrnormalizer.config.normalizerconfig.NormalizerConfig;
import VinSTTNorm.asrnormalizer.config.normalizerconfig.VoiceControlNormalizerConfig;
import VinSTTNormV2.config.OnlineConfig;
import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanNormalizer.BaseNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.AbbreviationNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.CharacterLexiconNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.LexiconNormalizer;
import VinSTTNormV2.utilities.Utilities;
import org.json.JSONObject;



public class ExoticHandler extends BaseHandler{

    public ExoticHandler(){
        NormalizerConfig voiceControlNormalizerConfig = new VoiceControlNormalizerConfig();
        JSONObject config = voiceControlNormalizerConfig.getConfig();

        this.normalizers.add(new AbbreviationNormalizer(config));
        this.normalizers.add(new LexiconNormalizer(config));
        this.normalizers.add(new CharacterLexiconNormalizer(config));

    }


}
