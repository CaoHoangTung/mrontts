package VinSTTNormV2.modelOuputHandler;

import VinSTTNormV2.config.OnlineConfig;
import VinSTTNormV2.spanNormalizer.exotic.AbbreviationNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.CharacterLexiconNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.LexiconNormalizer;
import VinSTTNormV2.spanNormalizer.link.WebNormalizer;
import org.json.JSONObject;

public class LinkHandler extends BaseHandler{

    public LinkHandler(){
        OnlineConfig onlineConfig = new OnlineConfig();
        JSONObject config = onlineConfig.getConfig();

        this.normalizers.add(new WebNormalizer(config));
    }
}
