package VinSTTNorm.asrnormalizer;

import VinSTTNorm.asrnormalizer.config.normalizerconfig.VinFastVoiceControlNormalizerConfig;
import VinSTTNorm.asrnormalizer.normalizer.BaseNormalizer;
import VinSTTNorm.speech.asr.INormalizer;

public class TestNormalizer {
    public static void main(String[] args) {
        INormalizer normalizer = new VinFastVoiceControlAsrNormalizer();

        String text = "Bật điều hòa lên hai sáu độ rưỡi";
        String output = normalizer.normText(text);

        System.out.println(String.format("Input: %s\nOutput: %s", text, output));
    }
}
