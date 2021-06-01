package VinSTTNormV2.utilities.fst;

import java.util.List;

public class GraphNode {
    int nodeIdx;
    List<GraphEdge> edges;
    boolean isEndState;

    public GraphNode(int nodeIdx, List edges, boolean isEndState) {
        this.nodeIdx = nodeIdx;
        this.edges = edges;
        this.isEndState = isEndState;
    }

    public int getNodeIdx() {
        return nodeIdx;
    }

    public boolean isEndState() {
        return isEndState;
    }

    @Override
    public String toString() {
        return "GraphNode{" +
                "nodeIdx=" + nodeIdx +
                ", edges=" + edges +
                ", isEndState=" + isEndState +
                '}';
    }
}