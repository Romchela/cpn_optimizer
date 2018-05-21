package iis.nsu.vishnevskii.cpn.optimizer;

import iis.nsu.vishnevskii.cpn.GraphModifier;
import iis.nsu.vishnevskii.cpn.GraphUtils;
import iis.nsu.vishnevskii.cpn.model.CPNGraph;
import iis.nsu.vishnevskii.cpn.model.CPNGraph.VertexType;
import iis.nsu.vishnevskii.cpn.model.ChangesQueue;
import java.util.List;

public class MergerOptimizer extends CPNSeparatePageOptimizer {

  private boolean optimizeFromVertex(int place, CPNGraph graph, ChangesQueue queue) {

    if (GraphUtils.countOutgoingArcs(place, graph) != 1) {
      return false;
    }

    int nextTrans = graph.getArcs(place).get(0).getTo();
    String nextArc1 = graph.getArcs(place).get(0).getArcId();
    if (GraphUtils.countOutgoingArcs(nextTrans, graph) != 1) {
      return false;
    }

    int nextPlace = graph.getArcs(nextTrans).get(0).getTo();
    String nextArc2 = graph.getArcs(nextTrans).get(0).getArcId();

    List<Integer> incomingTransList = GraphUtils.getIncomingArcs(place, graph);
    if (incomingTransList.isEmpty()) {
      return false;
    }

    boolean failedNeighbour = false;
    for (int prevTrans : incomingTransList) {
      List<Integer> incomingPlaces = GraphUtils.getIncomingArcs(prevTrans, graph);
      if (incomingPlaces.size() != 1) {
        failedNeighbour = true;
        continue;
      }
      int prevPlace = incomingPlaces.get(0);
      if (GraphUtils.countOutgoingArcs(prevPlace, graph) != 1) {
        failedNeighbour = true;
        continue;
      }

      String prevArc1 = graph.getArcs(prevTrans).get(0).getArcId();
      System.out.println("Merger   Optimization");
      GraphModifier.replaceArcToValue(prevTrans, place, nextPlace, graph, queue);
      // TODO: GraphModifier.setExpression(prevArc1, newExpression);
    }

    if (!failedNeighbour) {
      GraphModifier.deleteOutgoingArcs(place, graph, queue);
      GraphModifier.deleteOutgoingArcs(nextTrans, graph, queue);
      GraphModifier.deletePlace(place, graph, queue);
      GraphModifier.deleteTransition(nextTrans, graph, queue);
    }

    return false;
  }

  @Override
  CPNGraph optimizePage(CPNGraph graph, ChangesQueue queue) {
    boolean optimized = false;
    do {
      for (int i = 0; i < graph.getVertexNumber(); i++) {
        if (graph.getType(i) == VertexType.PLACE) {
          //optimized |= optimizeFromVertex(i, graph, queue);
        }
      }
    } while (optimized);
    return graph;
  }
}
