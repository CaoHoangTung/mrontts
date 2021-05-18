package VinSTTNorm.asrnormalizer.config.normalizerconfig;

import org.json.JSONObject;

abstract public class NormalizerConfig {
    protected JSONObject config;

    public JSONObject getConfig() {
        return config;
    }

}
