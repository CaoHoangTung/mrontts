package VinSTTNorm.asrnormalizer;

import VinSTTNorm.speech.asr.INormalizer;

public class Main {
    public static void main(String[] args) {
        INormalizer normalizer = new VinFastVoiceControlAsrNormalizer();

//        String text = "ngày mười chín tháng năm năm hai không hai mốt và ba trăm hai chín";
        String text = "một triệu bốn trăm ba mươi tám ngàn ba trăm hai chín";

        String output = normalizer.normText(text);

        System.out.println(String.format("Input: %s\nOutput: %s", text, output));
    }
}
