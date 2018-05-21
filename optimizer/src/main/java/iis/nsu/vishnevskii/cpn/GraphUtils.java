package iis.nsu.vishnevskii.cpn;

import iis.nsu.vishnevskii.cpn.model.Arc;
import iis.nsu.vishnevskii.cpn.model.CPNGraph;
import iis.nsu.vishnevskii.cpn.model.CPNVertex;
import java.util.ArrayList;
import java.util.List;

public class GraphUtils {

  public static int countOutgoingArcs(int vertex, CPNGraph graph) {
    return graph.getArcs(vertex).size();
  }

  public static int countIncomingArcs(int vertex, CPNGraph graph) {
    int cntIncoming = 0;
    for (int i = 0; i < graph.size(); i++) {
      for (Arc a : graph.getArcs(i)) {
        if (a.getTo() == vertex) {
          cntIncoming++;
        }
      }
    }
    return cntIncoming;
  }

  public static List<Integer> getIncomingArcs(int vertex, CPNGraph graph) {
    List<Integer> incoming = new ArrayList<>();
    for (int i = 0; i < graph.size(); i++) {
      for (Arc a : graph.getArcs(i)) {
        if (a.getTo() == vertex) {
          incoming.add(i);
        }
      }
    }
    return incoming;
  }

  public static List<Integer> getStartedPlaces(CPNGraph graph) {
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < graph.getVertexNumber(); i++) {
      if (graph.isStarted(i)) {
        result.add(i);
      }
    }
    return result;
  }

}
