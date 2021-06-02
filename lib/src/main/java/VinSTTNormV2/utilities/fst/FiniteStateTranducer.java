package VinSTTNormV2.utilities.fst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FiniteStateTranducer {
    public JSONObject config;
    public List<GraphNode> graphNodes = new ArrayList<>();

    public FiniteStateTranducer(JSONArray configItems) throws JSONException {
        for (int idx = 0 ; idx < configItems.length() ; idx++) {
            JSONObject nodeJSON = (JSONObject) configItems.get(idx);
            JSONArray edgesJSON = (JSONArray) nodeJSON.get("edges");

            int nodeIdx = (int) nodeJSON.get("node_id");
            boolean isEndState = (boolean) nodeJSON.get("is_end");

            List<GraphEdge> edges = new ArrayList<>();
            for (int edgeIdx = 0 ; edgeIdx < edgesJSON.length() ; edgeIdx++) {
                JSONArray edgeJSONArray = edgesJSON.getJSONArray(edgeIdx);
                int nextNodeIdx = (int) edgeJSONArray.get(0);
                String inputTape = (String) edgeJSONArray.get(1);
                String outputTape = (String) edgeJSONArray.get(2);

                GraphEdge edge = new GraphEdge(nodeIdx, nextNodeIdx, inputTape, outputTape);
                edges.add(edge);
            }
            this.graphNodes.add(nodeIdx, new GraphNode(nodeIdx, edges, isEndState));
        }
    }

    public GraphNode getStartNode() {
        return this.graphNodes.size() > 0 ? this.graphNodes.get(0) : null;
    }

    private GraphEdge getEdgePath(List<GraphEdge> edges, String inputTape) {
        GraphEdge resultEdge = null;
        GraphEdge exceptionEdge = null;
        for (GraphEdge edge : edges) {
            if (edge.inputTape.equals(inputTape)) {
                resultEdge = edge;
            }
            if (edge.inputTape.equals("<else>")) {
                exceptionEdge = edge;
            }
        }
        if (resultEdge == null) {
            if (exceptionEdge != null) {
                resultEdge = exceptionEdge;
            }
        }
        return resultEdge;
    }

    private int getPositionalAction(String outputTape) {
        return Integer.valueOf(outputTape.split("\\|")[2]);
    }

    /**
     * Process the current state with an output tape value and return the new state
     * @param currentState
     * @param outputTape
     * @return
     * TODO
     */
    private String processState(String currentState, String outputTape) {
        String[] outputTapeItems = outputTape.split("\\|");

        String outputValueString = outputTapeItems[0];
        String actionString = outputTapeItems[1];

        String[] actions = actionString.split(";");

        for (String action : actions) {
            if (action.length() > 0) {
                String[] actionComponents = action.split(" ");
                String actionName = actionComponents[0];
                int actionQty = Integer.valueOf(actionComponents[1]);

                switch (actionName) {
                    case "<del>":
                        currentState = currentState.substring(0, currentState.length()-actionQty);
                        break;
                }
            }
        }
        currentState += outputValueString;

        return currentState;
    }

    /**
     * Return the next node in the graph and the next state after applying the output tape to the currentStateString
     * @param currentNode: the current node in graph
     * @param currentState: the string of the current output
     * @param inputTape: the input tape to be compared to currentNode's edges
     * @return nextNode, nextState, positionalAction
     * GraphNode nextNode is the next node in the graph
     * String nextState is the next state after applying changes
     * int positionalAction is the action on the string tokens position (can be understood as timestep)
     * the next position will be calculated as: nextPos = prevPos + positionalAction
     */
    public TraverseState getNextState(GraphNode currentNode, String currentState, String inputTape) {
        List edges = currentNode.edges;

        GraphEdge pathToGo = this.getEdgePath(edges, inputTape);
        if (pathToGo != null) {
            GraphNode nextNode = graphNodes.get(pathToGo.dstNodeIdx);
            String outputTape = pathToGo.outputTape;

            return new TraverseState(
                    nextNode,
                    this.processState(currentState, outputTape),
                    this.getPositionalAction(outputTape)
            );
        } else {
            return null;
        }
    }
}

