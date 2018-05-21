package iis.nsu.vishnevskii.cpn.optimizer;

import iis.nsu.vishnevskii.cpn.GraphModifier;
import iis.nsu.vishnevskii.cpn.GraphUtils;
import iis.nsu.vishnevskii.cpn.model.Arc;
import iis.nsu.vishnevskii.cpn.model.CPNGraph;
import iis.nsu.vishnevskii.cpn.model.CPNGraph.VertexType;
import iis.nsu.vishnevskii.cpn.model.CPNNet;
import iis.nsu.vishnevskii.cpn.model.ChangesQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UselessOptimizer implements CPNOptimizer {

  public class VertexInfo {
    private int vertex;
    private CPNGraph graph;

    public VertexInfo(int vertex, CPNGraph graph) {
      this.vertex = vertex;
      this.graph = graph;
    }

    public int getVertex() {
      return vertex;
    }

    public void setVertex(int vertex) {
      this.vertex = vertex;
    }

    public CPNGraph getGraph() {
      return graph;
    }

    public void setGraph(CPNGraph graph) {
      this.graph = graph;
    }
  }

  private void dfs(CPNGraph graph, int vertex, List<Boolean> used) {
    used.set(vertex, true);
    for (Arc arc : graph.getArcs(vertex)) {
      if (!used.get(arc.getTo())) {
        dfs(graph, arc.getTo(), used);
      }
    }
  }

  @Override
  public CPNNet optimize(CPNNet net, ChangesQueue queue) {

    Map<String, List<VertexInfo>> fusionInfoList = new HashMap<>();
    Map<String, Boolean> fusionInfoUsed = new HashMap<>();
    for (CPNGraph graph : net.getPages().values()) {
      for (int i = 0; i < graph.getVertexNumber(); i++) {
        if (graph.getType(i).equals(VertexType.PLACE)) {
          String fusionInfoText = graph.getFusionInfo(i);
          if (fusionInfoText != null) {
            fusionInfoUsed.putIfAbsent(fusionInfoText, false);
            if (!fusionInfoList.containsKey(fusionInfoText)) {
              fusionInfoList.put(fusionInfoText, new ArrayList<>());
            }
            fusionInfoList.get(fusionInfoText).add(new VertexInfo(i, graph));
          }
        }
      }
    }

    for (Map.Entry<String, CPNGraph> entry : net.getPages().entrySet()) {
      List<Boolean> used = getUsedVertices(entry.getValue());
      CPNGraph graph = entry.getValue();

      for (int i = 0; i < graph.getVertexNumber(); i++) {
        String fusionInfoText = graph.getFusionInfo(i);
        if (!used.get(i) ) {
          if (fusionInfoText != null) {
            continue;
          }

          deleteVertex(graph, i, queue);
        } else {
          fusionInfoUsed.put(fusionInfoText, true);
        }
      }
    }

    for (String fusionInfoText : fusionInfoUsed.keySet()) {
      if (fusionInfoUsed.get(fusionInfoText).equals(Boolean.FALSE)) {
        for (VertexInfo vertexInfo : fusionInfoList.get(fusionInfoText)) {
          deleteVertex(vertexInfo.getGraph(), vertexInfo.getVertex(), queue);
        }
      }
    }

    return net;
  }

  private void deleteVertex(CPNGraph graph, int i, ChangesQueue queue) {
    VertexType type = graph.getType(i);
    GraphModifier.deleteOutgoingArcs(i, graph, queue);

    System.out.println("UselessOptimizer: " + graph.getId(i));

    if (type.equals(VertexType.PLACE)) {
      GraphModifier.deletePlace(i, graph, queue);
    } else if (type.equals(VertexType.TRANSITION)) {
      GraphModifier.deleteTransition(i, graph, queue);
    }
  }

  private List<Boolean> getUsedVertices(CPNGraph graph) {
    List<Integer> startedPlaces = GraphUtils.getStartedPlaces(graph);
    List<Boolean> used = new ArrayList<>(Collections.nCopies(graph.getVertexNumber(), false));
    for (Integer place : startedPlaces) {
      dfs(graph, place, used);
    }

    return used;
  }
}
