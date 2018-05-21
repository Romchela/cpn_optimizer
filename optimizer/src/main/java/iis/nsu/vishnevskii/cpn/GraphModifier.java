package iis.nsu.vishnevskii.cpn;

import iis.nsu.vishnevskii.cpn.change.DeletePlaceChange;
import iis.nsu.vishnevskii.cpn.change.DeleteArcChange;
import iis.nsu.vishnevskii.cpn.change.DeleteTransitionChange;
import iis.nsu.vishnevskii.cpn.change.ReplaceExpressionChange;
import iis.nsu.vishnevskii.cpn.change.ReplaceFromValueChange;
import iis.nsu.vishnevskii.cpn.change.ReplaceToValueChange;
import iis.nsu.vishnevskii.cpn.model.Arc;
import iis.nsu.vishnevskii.cpn.model.CPNGraph;
import iis.nsu.vishnevskii.cpn.model.CPNVertex.Type;
import iis.nsu.vishnevskii.cpn.model.ChangesQueue;

public class GraphModifier {

  public static void combinePlaces(int place1, int place2, CPNGraph graph, ChangesQueue queue) {
    queue.add(new DeletePlaceChange(graph.getId(place2)));
    for (Arc arc : graph.getArcs(place2)) {
      queue.add(new ReplaceFromValueChange(arc.getArcId(), graph.getId(place1), Type.PLACE));
    }
    graph.combinePlaces(place1, place2);
  }

  public static void setExpression(String arcId, String expression, CPNGraph graph, ChangesQueue queue) {
    graph.setExpression(arcId, expression);
    queue.add(new ReplaceExpressionChange(arcId, expression));
  }

  public static void deleteTransition(int trans, CPNGraph graph, ChangesQueue queue) {
    queue.add(new DeleteTransitionChange(graph.getId(trans)));
    graph.deleteTransition(trans);
  }

  public static void deletePlace(int place, CPNGraph graph, ChangesQueue queue) {
    queue.add(new DeletePlaceChange(graph.getId(place)));
    graph.deletePlace(place);
  }

  public static void deleteIncomingArcs(int vertex, CPNGraph graph, ChangesQueue queue) {
    for (int i = 0; i < graph.size(); i++) {
      for (int j = 0; j < graph.getArcs(i).size(); j++) {
        Arc arc = graph.getArcs(i).get(j);
        if (arc.getTo() == vertex) {
          queue.add(new DeleteArcChange(arc.getArcId()));
          graph.deleteArc(i, vertex);
          j--;
        }
      }
    }
  }

  public static void deleteOutgoingArcs(int vertex, CPNGraph graph, ChangesQueue queue) {
    while (!graph.getArcs(vertex).isEmpty()) {
      Arc arc = graph.getArcs(vertex).get(0);
      queue.add(new DeleteArcChange(arc.getArcId()));
      graph.deleteArc(vertex, arc.getTo());
    }
  }

  public static void replaceArcToValue(int source, int prevEnd, int newEnd, CPNGraph graph, ChangesQueue queue) {
    String arcId = null;
    for (int i = 0; i < graph.getArcs(source).size(); i++) {
      if (graph.getArcs(source).get(i).getTo() == prevEnd) {
        graph.getArcs(source).get(i).setTo(newEnd);
        arcId = graph.getArcs(source).get(i).getArcId();
      }
    }
    if (arcId == null) {
      throw new RuntimeException("arcId is not initialised");
    }
    queue.add(new ReplaceToValueChange(arcId, graph.getId(newEnd), Type.TRANSITION));
  }

}
