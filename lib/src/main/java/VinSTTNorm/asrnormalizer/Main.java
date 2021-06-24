package VinSTTNorm.asrnormalizer;

import VinSTTNorm.speech.asr.INormalizer;

public class Main {
    public static void main(String[] args) {
        INormalizer normalizer = new VinFastVoiceControlAsrNormalizer();

//        String text = "ngày mười chín tháng năm năm hai không hai mốt và ba trăm hai chín";
//        String text = "bảy một năm ba năm bốn chấm bảy ba năm một không tám chín chín năm lần";
//
//        String output = normalizer.normText(text);
//
//        System.out.println(String.format("Input: %s\nOutput: %s", text, output));

        Benchmarker benchmark = new Benchmarker(normalizer);

        String result = benchmark.evaluate("/home/trith/Work/STT-norm/stt-norm-java/lib/src/main/resources/testcase/navigation_custom01/spoken",
                            "/home/trith/Work/STT-norm/stt-norm-java/lib/src/main/resources/testcase/navigation_custom01/written",
                            false, false);

        System.out.println(result);
    }
}
