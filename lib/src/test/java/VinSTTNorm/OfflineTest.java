package VinSTTNorm;

import VinSTTNormV2.OfflineNormalizer;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class OfflineTest {
    @Test
    public void test() {
        String text1 = "căn của hai mươi cộng căn hai năm sáu";
        OfflineNormalizer normalizer = new OfflineNormalizer();
        System.out.println(String.format("Input: %s\nOutput: %s", text1, normalizer.normText(text1)));
    }
}
