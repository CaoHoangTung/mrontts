package VinSTTNorm;

import VinSTTNormV2.OfflineNormalizer;
import org.junit.Test;


public class RunBenchmark {
    @Test
    public void test() {
        OfflineNormalizer normalizer = new OfflineNormalizer();

        Benchmarker benchmarker = new Benchmarker(normalizer);
        String result = benchmarker.evaluate("/home/trith/Work/STT-norm/stt-norm-java/lib/src/main/resources/testcase/calculations/spoken",
                                             "/home/trith/Work/STT-norm/stt-norm-java/lib/src/main/resources/testcase/calculations/written");

        System.out.println(result);
    }
}
