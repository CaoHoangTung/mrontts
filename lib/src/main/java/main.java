import VinSTTNormV2.Benchmarker;
import VinSTTNormV2.OfflineNormalizer;
import VinSTTNormV2.modelOuputNormalizer.*;

import java.util.Hashtable;

public class main {
    public static void main(String[] args) {
        String text1 = "bảy một năm chấm bảy một năm năm";
        OfflineNormalizer normalizer = new OfflineNormalizer();
        System.out.println(String.format("Input: %s\nOutput: %s", text1, normalizer.normText(text1)));


//        Hashtable<String, BaseOutputNormalizer> handlers = new Hashtable<>();
//
//        handlers.put("exotic", new ExoticNormalizer());
//        handlers.put("link", new LinkOutputNormalizer());
//        handlers.put("number", new NumberOutputNormalizer());
//        handlers.put("date", new DateNormalizer());
//        handlers.put("address", new AddressNormalizer());
//        handlers.put("normal", new MixOutputNormalizer());
//        handlers.put("score", new ScoreOutputNormalizer());
//
//
//        String[] text = {"một ki lô mét rưỡi"};
//        String[] term = {"number"};
//
//        for (int i = 0; i < text.length; i++){
//            String normedText = handlers.get(term[i]).normAll(text[i]);
//            System.out.println(normedText);
//        }

        Benchmarker benchmarker = new Benchmarker(normalizer);
        String result = benchmarker.evaluate("/home/trith/Work/STT-norm/stt-norm-java/lib/src/main/resources/testcase/navigation/spoken",
                                             "/home/trith/Work/STT-norm/stt-norm-java/lib/src/main/resources/testcase/navigation/written");

        System.out.println(result);
    }

}
