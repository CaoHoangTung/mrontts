import VinSTTNorm.asrnormalizer.config.normalizerconfig.NormalizerConfig;
import VinSTTNorm.asrnormalizer.config.normalizerconfig.VoiceControlNormalizerConfig;
import VinSTTNormV2.modelOuputHandler.BaseHandler;
import VinSTTNormV2.modelOuputHandler.ExoticHandler;
import VinSTTNormV2.modelOuputHandler.LinkHandler;
import VinSTTNormV2.modelOuputHandler.NumberNormalizer;
import VinSTTNormV2.spanExtractor.SpanObject;
import VinSTTNormV2.spanExtractor.exotic.AbbreviationExtractor;
import VinSTTNormV2.spanExtractor.exotic.LexiconExtractor;
import VinSTTNormV2.spanExtractor.number.date.MonthExtractor;
import VinSTTNormV2.spanExtractor.number.date.YearExtractor;
import VinSTTNormV2.spanExtractor.number.time.TimeExtractor;
import VinSTTNormV2.spanNormalizer.exotic.AbbreviationNormalizer;
import VinSTTNormV2.spanNormalizer.exotic.LexiconNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.MonthNormalizer;
import VinSTTNormV2.spanNormalizer.number.date.YearNormalizer;
import VinSTTNormV2.spanNormalizer.number.time.TimeNormalizer;
import VinSTTNormV2.utilities.Utilities;
import org.json.JSONObject;

import java.util.Hashtable;

public class main {
    public static void main(String[] args) {
        NormalizerConfig voiceControlNormalizerConfig = new VoiceControlNormalizerConfig();
        JSONObject config = voiceControlNormalizerConfig.getConfig();

        String text = "";
        ExtractorAndNorm[] terms = new ExtractorAndNorm[]{
//                new ExtractorAndNorm(new LexiconExtractor(config), new LexiconNormalizer(config)),
//                new ExtractorAndNorm(new AbbreviationExtractor(config), new AbbreviationNormalizer(config)),
//                new ExtractorAndNorm(new MonthExtractor(config), new MonthNormalizer(config)),
                new ExtractorAndNorm(new YearExtractor(config), new YearNormalizer(config)),
//                new ExtractorAndNorm(new TimeExtractor(config), new TimeNormalizer(config)),


        };
        for(ExtractorAndNorm term: terms){
            SpanObject[] spans = term.extractor.getSpans(text);
            term.normalizer.doAllNorm(spans);
            for(SpanObject span : spans){
                System.out.println(span.toString());
            }
            text = Utilities.replaceString(text, spans);
        }
        System.out.println(text);

//        String text2 = "bác hồ sinh năm một chín chín tám và đếm ba hai một";
//        FSTSerialNumberextractor numberExtractor = new FSTSerialNumberextractor(config);
//        SpanObject[] spans = numberExtractor.getSpans(text2);
//        for(SpanObject span : spans){
//            System.out.println(span.toString());
//        }
//        VinFastVoiceControlAsrNormalizer myNormalizer = new VinFastVoiceControlAsrNormalizer();
////        String text = "tăng âm lượng thêm năm mươi phần trăm";
//        String output = myNormalizer.normText(text);
//        System.out.println(output);

//        Hashtable<String, BaseHandler> handlers = new Hashtable<>();
//
//        handlers.put("exotic", new ExoticHandler());
//        handlers.put("link", new LinkHandler());
//        handlers.put("normal", new NumberNormalizer());
//
//
//        String[] text = {"Anh", "Ba Rọi Béo", "hai ba tuổi sinh ngày mười chín tháng năm năm một chín tám chín vào lúc hai giờ kém mười lăm và ba giờ bốn lăm nặng hai phẩy ba ki lô gam và được phát sóng trên", "Vê Tê Vê Một"};
//        String[] term = {"normal", "exotic", "normal", "exotic"};
//        for (int i = 0; i < text.length; i++){
//            String normedText = handlers.get(term[i]).normAll(text[i]);
//            System.out.println(normedText);
//        }
    }
}
