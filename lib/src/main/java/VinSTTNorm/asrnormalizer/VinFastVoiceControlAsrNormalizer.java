package VinSTTNorm.asrnormalizer;

import VinSTTNorm.asrnormalizer.datetime.TimeEntityRegexEntity;
import VinSTTNorm.asrnormalizer.normalizer.BaseNormalizer;
import VinSTTNorm.asrnormalizer.numerical.*;
import VinSTTNorm.asrnormalizer.personname.PersonNameEntity;
import VinSTTNorm.asrnormalizer.personname.SpecialFullNameEntity;
import VinSTTNorm.asrnormalizer.propername.*;
import VinSTTNorm.asrnormalizer.stringreplacer.*;
import VinSTTNorm.asrnormalizer.vfast.VinFastAirConditionerNumberEntity;
import VinSTTNorm.speech.asr.INormalizer;

import VinSTTNormV2.config.OfflineConfig;
import VinSTTNormV2.config.NormalizerConfig;
import org.json.JSONObject;

/**
 * The main class of the package. Use to normalize text
 */
public class VinFastVoiceControlAsrNormalizer implements INormalizer {
    private static BaseNormalizer[] normalizers;

    private static VinFastVoiceControlAsrNormalizer INSTANCE = null;
    private static final Object LOCK = new Object();

    public VinFastVoiceControlAsrNormalizer() {
        init();

        this.normText(""); // dummy norm to avoid long infer time on the first time
    }

    private void init() {
        NormalizerConfig vFastNormalizerConfig = new OfflineConfig();
        JSONObject config = vFastNormalizerConfig.getConfig();

        // initiate pipeline
        this.normalizers = new BaseNormalizer[]{
                new BaseNormalizer(new LexiconRegexEntity(config)), // vê tê vê => vtv

                new BaseNormalizer(new SpecialFullNameEntity(config)), // phil mickelson => Phil Mickelson
                new BaseNormalizer(new PersonNameEntity(config)), // cao hoàng tùng => Cao Hoàng Tùng


//                new BaseNormalizer(new BinaryProperNameMapEntity(config)),
                new BaseNormalizer(new SameDictNameMapEntity(config)),

                new BaseNormalizer(new SerialDigitNumberFSTEntity(config)), // một hai bảy tám sáu chín ba => 1278693

                new BaseNormalizer(new MonthRegexEntity(config)), // tháng mười hai => tháng 12
                new BaseNormalizer(new YearRegexEntity(config)), // năm hai ngàn lẻ sáu => năm 2006
                new BaseNormalizer(new MonthYearCountRegexEntity(config)), // ba năm => 3 năm

                new BaseNormalizer(new OneTwoThreeDigitNumberFSTEntity(config)), // một triệu hai trăm ba mươi ngàn => 1 triệu 230 ngàn
                new BaseNormalizer(new ThousandNumberFSTEntity(config)), // 1 triệu 230 ngàn => 1 triệu 230000
//                new BaseNormalizer(new MillionNumberFSTEntity(config)), // 1 triệu 230000 => 1230000
//                new BaseNormalizer(new BillionNumberFSTEntity(config)), // 123 tỉ => 123000000000

                new BaseNormalizer(new NumberPunctuationRegexEntity(config)), // 1 phẩy 3 => 1.3 ; 1 trên 3 trên 8 => 1/3/8
                new BaseNormalizer(new VinFastAirConditionerNumberEntity(config)), // ba mốt độ rưỡi => 31.5 độ
                new BaseNormalizer(new TimeEntityRegexEntity(config)), // 1 giờ 10 => 1:10

                new BaseNormalizer(new CharacterLexiconRegexEntity(config)), // a bờ cờ => ABC
                new BaseNormalizer(new AbbreviationMapEntity(config)), // vtv => VTV

                new BaseNormalizer(new UnitRegexEntity(config)),  // 1 ki lô mét => 1 km

                new BaseNormalizer(new SegmentRegexEntity(config)),  // 3 % => 3% ; 3 d => 3d
                new BaseNormalizer(new WrittenSerialNumberCharacterRegexEntity(config)),  // 11db6 => 11DB6



                new BaseNormalizer(new WebsiteNameMapEntity(config)),  // google chấm com chấm vn => google.com.vn
                new BaseNormalizer(new AppNameMapEntity(config)),

                new BaseNormalizer(new OutputSmothingMapEntity(config)) // Đắc Lắc => Đắk Lắk
        };
    }

    /**
     * Norm text.
     * Usage: result = Normalizer.normText(yourText)
     */
    @Override
    public String normText(String text) {
        text = text.replaceAll(" +", " ");
        text = text.trim();

        text = "<s> " + text + " </s>";

        for (BaseNormalizer normalizer : normalizers) {
            text = normalizer.normFullText(text);
        }

        text = text.substring(4, text.length() - 5);

        return text;
    }
}
