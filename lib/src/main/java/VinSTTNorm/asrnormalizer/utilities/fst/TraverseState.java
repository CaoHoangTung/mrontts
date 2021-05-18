package VinSTTNorm.asrnormalizer.utilities.fst;

public final class TraverseState {
    public GraphNode node;
    public String currentState;
    public int positionalAction;

    public TraverseState(GraphNode node, String currentState, int positionalAction) {
        this.node = node;
        this.currentState = currentState;
        this.positionalAction = positionalAction;
    }

    @Override
    public String toString() {
        return "TraverseState{" +
                "node=" + node.nodeIdx +
                ", currentState='" + currentState + '\'' +
                ", positionalAction=" + positionalAction +
                '}';
    }
}
