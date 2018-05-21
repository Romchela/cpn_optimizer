package iis.nsu.vishnevskii.cpn.optimizer;

import static iis.nsu.vishnevskii.cpn.model.CPNGraph.VertexType.PLACE;

import iis.nsu.vishnevskii.cpn.GraphModifier;
import iis.nsu.vishnevskii.cpn.GraphUtils;
import iis.nsu.vishnevskii.cpn.model.CPNGraph;
import iis.nsu.vishnevskii.cpn.model.ChangesQueue;

public class ForkOptimizer extends CPNSeparatePageOptimizer {

  private int a = 0;

  private boolean chainLookingAndOptimizing(int vertex, CPNGraph graph, ChangesQueue queue) {
    boolean isOptimized = false;
    if (GraphUtils.countOutgoingArcs(vertex, graph) != 1) {
      return false;
    }

    int nextTrans = graph.getArcs(vertex).get(0).getTo();
    String arcId1 = graph.getArcs(vertex).get(0).getArcId();
    String expression1 = graph.getExpression(arcId1);

    if (GraphUtils.countIncomingArcs(nextTrans, graph) == 1
      && GraphUtils.countOutgoingArcs(nextTrans, graph) == 1) {

      int nextPlace = graph.getArcs(nextTrans).get(0).getTo();
      String arcId2 = graph.getArcs(nextTrans).get(0).getArcId();
      String expression2 = graph.getExpression(arcId2);


      if (GraphUtils.countIncomingArcs(nextPlace, graph) == 1) {
        boolean isCorrectToReduce = true;
        for (int i = 0; i < graph.getArcs(nextPlace).size(); i++) {
          String expression3 = graph.getExpression(graph.getArcs(nextPlace).get(i).getArcId());
          isCorrectToReduce &= ExpressionCalculator.isCorrectToReduce(expression1, expression2, expression3);
        }

        if (isCorrectToReduce ) {
          if (a == 0 || a == 9) {
            System.out
              .println(a + " ChainOptimized: " + graph.getId(vertex) + " " + graph.getId(nextPlace));
            System.out.println("vertex: " + graph.getId(vertex));
            System.out.println("arcId1: " + arcId1);
            System.out.println("nextTrans: " + graph.getId(nextTrans));
            System.out.println("arcId2: " + arcId2);
            System.out.println("nextPlace: " + graph.getId(nextPlace));

            GraphModifier.deleteOutgoingArcs(vertex, graph, queue);
            GraphModifier.deleteIncomingArcs(nextPlace, graph, queue);
            GraphModifier.deleteTransition(nextTrans, graph, queue);
            GraphModifier.combinePlaces(vertex, nextPlace, graph, queue);
            for (int i = 0; i < graph.getArcs(nextPlace).size(); i++) {
              String arcId3 = graph.getArcs(nextPlace).get(i).getArcId();
              String expression3 = graph.getExpression(arcId3);
              System.out.println("arcId3: " + arcId3);
              String finalExpression = ExpressionCalculator
                .calculateExpression(expression1, expression2, expression3);
              GraphModifier.setExpression(arcId3, finalExpression, graph, queue);
            }
            isOptimized = true;
            System.out.println("----------------");
          }

          a++;
        }
      }
    }
    return isOptimized;
  }


  @Override
  public CPNGraph optimizePage(CPNGraph graph, ChangesQueue queue) {
    while (true) {
      boolean found = false;

      for (int i = 0; i < graph.size(); i++) {
        if (graph.getType(i) == PLACE) {
          found |= chainLookingAndOptimizing(i, graph, queue);
        }
      }

      if (!found) {
        break;
      }
    }
    return graph;
  }
}
