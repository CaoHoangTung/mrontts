package VinSTTNorm.asrnormalizer;

import VinSTTNorm.asrnormalizer.config.normalizerconfig.NormalizerConfig;
import VinSTTNorm.asrnormalizer.config.normalizerconfig.VKeyNormalizerConfig;
import VinSTTNorm.asrnormalizer.datetime.TimeEntityRegexEntity;
import VinSTTNorm.asrnormalizer.normalizer.BaseNormalizer;
import VinSTTNorm.asrnormalizer.numerical.*;
import VinSTTNorm.asrnormalizer.personname.PersonNameEntity;
import VinSTTNorm.asrnormalizer.personname.SpecialFullNameEntity;
import VinSTTNorm.asrnormalizer.propername.*;
import VinSTTNorm.asrnormalizer.stringreplacer.*;
import VinSTTNorm.asrnormalizer.vkeyboard.VKeyboardMatchResultNumberEntity;
import VinSTTNorm.speech.asr.INormalizer;
import org.json.JSONObject;

/**
 * The main class of the package. Use to normalize text
 */
public class VKeyAsrNormalizer implements INormalizer {
    private static BaseNormalizer[] normalizers;

    VKeyAsrNormalizer() {
        init();

        this.normText(""); // dummy norm to avoid long infer time on the first time
    }

    private void init() {
        NormalizerConfig vKeyNormalizerConfig = new VKeyNormalizerConfig();
        JSONObject config = vKeyNormalizerConfig.getConfig();

        // initiate pipeline
        this.normalizers = new BaseNormalizer[]{
                new BaseNormalizer(new LexiconRegexEntity(config)), // vê tê vê => vtv

                new BaseNormalizer(new SpecialFullNameEntity(config)), // phil mickelson => Phil Mickelson
                new BaseNormalizer(new PersonNameEntity(config)), // cao hoàng tùng => Cao Hoàng Tùng

//                new BaseNormalizer(new SameDictNameMapEntity(config)),
                new BaseNormalizer(new BinaryProperNameMapEntity(config)), // uppercase all proper name
//                new BaseNormalizer(new CountryMapEntity(config)), // việt nam => Việt Nam
//                new BaseNormalizer(new ProvinceMapEntity(config)), // hà nội => Hà Nội
//                new BaseNormalizer(new DistrictMapEntity(config)), // hai bà trưng => Hai Bà Trưng


//                new BaseNormalizer(new POIMapEntity(config)), // chùa trấn quốc => Chùa Trấn Quốc
//                new BaseNormalizer(new VinMapEntity(config)), // adayroi => Adayroi
//                new BaseNormalizer(new OtherMapEntity(config)), // alibaba => Alibaba
//                new BaseNormalizer(new TownMapEntity(config)), // thị trấn hương khê => thị trấn Hương Khê
//                new BaseNormalizer(new CommuneMapEntity(config)), // xã hồng việt => xã Hồng Việt
//                new BaseNormalizer(new WardMapEntity(config)), // phường nghĩa tân => phường Nghĩa Tân
//                new BaseNormalizer(new SportMapEntity(config)), // ac milan => AC Milan

                new BaseNormalizer(new SerialNumberRegexEntity(config)), // không ba tám ba sáu một không sáu bảy => 038361067
                new BaseNormalizer(new MonthRegexEntity(config)), // tháng mười hai => tháng 12
                new BaseNormalizer(new YearRegexEntity(config)), // năm hai ngàn lẻ sáu => năm 2006
                new BaseNormalizer(new MonthYearCountRegexEntity(config)), // ba năm => 3 năm

                new BaseNormalizer(new NumberRegexEntity(config)), // một triệu hai trăm ba mươi ngàn => 1230000

                new BaseNormalizer(new NumberPunctuationRegexEntity(config)), // 1 phẩy 3 => 1.3 ; 1 trên 3 trên 8 => 1/3/8
                new BaseNormalizer(new TimeEntityRegexEntity(config)), // 1 giờ 10 => 1:10
                new BaseNormalizer(new SqrtCalculationRegexEntity(config)),  // căn 2 => √2
                new BaseNormalizer(new SimpleCalculationRegexEntity(config)),  // 1 cộng 3 => 1 + 3
                new BaseNormalizer(new UnitRegexEntity(config)),  // 1 ki lô mét => 1 km
                new BaseNormalizer(new VKeyboardMatchResultNumberEntity(config)), // tỷ số 21 -> tỷ số 2:1

                new BaseNormalizer(new CharacterLexiconRegexEntity(config)), // a bờ cờ => ABC
                new BaseNormalizer(new AbbreviationMapEntity(config)), // vtv => VTV

                new BaseNormalizer(new SegmentRegexEntity(config)),  // 3 % => 3% ; 3 d => 3d
                new BaseNormalizer(new WrittenSerialNumberCharacterRegexEntity(config)),  // 11db6 => 11DB6

                new BaseNormalizer(new SentencePunctuationRegexEntity(config)), // Tôi yêu em chấm => Tôi yêu em.

                new BaseNormalizer(new AppNameMapEntity(config)),  // báo hay 24 giờ => Báo Hay 24h
                new BaseNormalizer(new WebsiteNameMapEntity(config)),  // google chấm com chấm vn => google.com.vn

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

        for (BaseNormalizer normalizer : normalizers) {
            text = normalizer.normFullText(text);
        }

        return text;
    }
}
