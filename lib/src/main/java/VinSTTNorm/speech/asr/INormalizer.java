package VinSTTNorm.speech.asr;

public interface INormalizer {
    String normText(String text);

    default String getType() {
        return "unknown";
    }
}