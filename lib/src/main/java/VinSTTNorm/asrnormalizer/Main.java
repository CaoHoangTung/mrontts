package VinSTTNorm.asrnormalizer;

import VinSTTNorm.speech.asr.INormalizer;

public class Main {
    public static void main(String[] args) {
        INormalizer normalizer = new VinFastVoiceControlAsrNormalizer();

//        String text = "ngày mười chín tháng năm năm hai không hai mốt và ba trăm hai chín";
        String text = "bật app việt theo cộng cộng";

        String output = normalizer.normText(text);

        System.out.println(String.format("Input: %s\nOutput: %s", text, output));

        Benchmarker benchmark = new Benchmarker(normalizer);

        String result = benchmark.evaluate("/home/trith/Work/STT-norm/stt-norm-java/lib/src/main/resources/testcase/datetime/spoken",
                            "/home/trith/Work/STT-norm/stt-norm-java/lib/src/main/resources/testcase/datetime/written",
                            false, false);

        System.out.println(result);
    }
}
