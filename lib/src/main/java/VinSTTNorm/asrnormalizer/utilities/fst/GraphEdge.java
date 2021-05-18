package VinSTTNorm.asrnormalizer.utilities.fst;

public class GraphEdge {
    int srcNodeIdx;
    int dstNodeIdx;
    String inputTape;
    String outputTape;

    public GraphEdge(int srcNodeIdx, int dstNodeIdx, String inputTape, String outputTape) {
        this.srcNodeIdx = srcNodeIdx;
        this.dstNodeIdx = dstNodeIdx;
        this.inputTape = inputTape;
        this.outputTape = outputTape;
    }

    @Override
    public String toString() {
        return "GraphEdge{" +
                "srcNodeIdx=" + srcNodeIdx +
                ", dstNodeIdx=" + dstNodeIdx +
                ", inputTape='" + inputTape + '\'' +
                ", outputTape='" + outputTape + '\'' +
                '}';
    }
}
