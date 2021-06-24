package VinSTTNormV2.utilities.fst;

public class GraphEdge {
    int srcNodeIdx;
    int dstNodeIdx;
    String inputTape;
    String outputTape;
    double weight;

    public GraphEdge(int srcNodeIdx, int dstNodeIdx, String inputTape, String outputTape, double weight) {
        this.srcNodeIdx = srcNodeIdx;
        this.dstNodeIdx = dstNodeIdx;
        this.inputTape = inputTape;
        this.outputTape = outputTape;
        this.weight = weight;
    }

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
