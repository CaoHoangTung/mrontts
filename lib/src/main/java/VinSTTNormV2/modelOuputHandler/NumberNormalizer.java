package VinSTTNormV2.modelOuputHandler;

import VinSTTNorm.asrnormalizer.config.normalizerconfig.NormalizerConfig;
import VinSTTNorm.asrnormalizer.config.normalizerconfig.VinFastVoiceControlNormalizerConfig;
import VinSTTNorm.asrnormalizer.datetime.TimeEntityRegexEntity;
import VinSTTNorm.asrnormalizer.normalizer.BaseNormalizer;
import VinSTTNorm.asrnormalizer.numerical.*;
import VinSTTNorm.asrnormalizer.stringreplacer.*;
import VinSTTNorm.asrnormalizer.vassistant.VinFastAirConditionerNumberEntity;
import VinSTTNorm.speech.asr.INormalizer;
import VinSTTNormV2.modelOuputHandler.BaseHandler;
import org.json.JSONObject;

public class NumberNormalizer extends BaseHandler {

    BaseNormalizer[] normalizer = null;

    public NumberNormalizer() {
        init();
        NormalizerConfig vFastNormalizerConfig = new VinFastVoiceControlNormalizerConfig();
        JSONObject config = vFastNormalizerConfig.getConfig();

        // initiate pipeline
        this.normalizer = new BaseNormalizer[]{

                new BaseNormalizer(new SerialDigitNumberFSTEntity(config)), // một hai bảy tám sáu chín ba => 1278693

                new BaseNormalizer(new MonthRegexEntity(config)), // tháng mười hai => tháng 12
                new BaseNormalizer(new YearRegexEntity(config)), // năm hai ngàn lẻ sáu => năm 2006
                new BaseNormalizer(new MonthYearCountRegexEntity(config)), // ba năm => 3 năm

                new BaseNormalizer(new OneTwoThreeDigitNumberFSTEntity(config)), // một triệu hai trăm ba mươi ngàn => 1 triệu 230 ngàn
                new BaseNormalizer(new ThousandNumberFSTEntity(config)), // 1 triệu 230 ngàn => 1 triệu 230000
                new BaseNormalizer(new MillionNumberFSTEntity(config)), // 1 triệu 230000 => 1230000
                new BaseNormalizer(new BillionNumberFSTEntity(config)), // 123 tỉ => 123000000000

                new BaseNormalizer(new NumberPunctuationRegexEntity(config)), // 1 phẩy 3 => 1.3 ; 1 trên 3 trên 8 => 1/3/8
                new BaseNormalizer(new VinFastAirConditionerNumberEntity(config)), // ba mốt độ rưỡi => 31.5 độ
                new BaseNormalizer(new TimeEntityRegexEntity(config)), // 1 giờ 10 => 1:10

        };
        this.normAll(""); // dummy norm to avoid long infer time on the first time
    }

    private void init() {

    }

    /**
     * Norm text.
     * Usage: result = Normalizer.normText(yourText)
     */
    @Override
    public String normAll(String text) {
        text = text.replaceAll(" +", " ");
        text = text.trim();

        text = "<s> " + text + " </s>";

        for (BaseNormalizer n : normalizer) {
            text = n.normFullText(text);
        }

        text = text.substring(4, text.length() - 5);

        return text;
    }
}
