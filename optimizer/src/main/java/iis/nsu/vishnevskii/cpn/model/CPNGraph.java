package iis.nsu.vishnevskii.cpn.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CPNGraph {

  public enum VertexType {
    PLACE, TRANSITION
  }

  private List<VertexType> typeByVertex;
  private List<String> idByVertex;
  private List<List<Arc>> graph;
  private Map<String, Integer> vertexById;
  private List<Boolean> isStartedPlace;
  private Map<Integer, String> pageByVertex;
  private Map<Integer, String> fusionInfoByVertex;
  private Map<String, String> expressionByArcId;

  public CPNGraph() {
    typeByVertex = new ArrayList<>();
    idByVertex = new ArrayList<>();
    graph = new ArrayList<>();
    vertexById = new HashMap<>();
    isStartedPlace = new ArrayList<>();
    pageByVertex = new HashMap<>();
    fusionInfoByVertex = new HashMap<>();
    expressionByArcId = new HashMap<>();
  }

  public VertexType getType(int v) {
    return typeByVertex.get(v);
  }

  public int size() {
    return graph.size();
  }

  public void addPlace(String placeId, boolean isStarted, String pageId, String fusionInfo) {
    typeByVertex.add(VertexType.PLACE);
    idByVertex.add(placeId);
    vertexById.put(placeId, graph.size());
    isStartedPlace.add(isStarted);
    pageByVertex.put(graph.size(), pageId);
    fusionInfoByVertex.put(graph.size(), fusionInfo);
    graph.add(new ArrayList<>());
  }

  public void addTrans(String transId, String cond, String code, String pageId) {
    typeByVertex.add(VertexType.TRANSITION);
    idByVertex.add(transId);
    vertexById.put(transId, graph.size());
    pageByVertex.put(graph.size(), pageId);
    isStartedPlace.add(false);
    graph.add(new ArrayList<>());
  }

  public List<Arc> getArcs(int vertex) {
    return graph.get(vertex);
  }

  public void addArc(String arcId, String orientation, String transend, String placeend, String expression) {
    expressionByArcId.put(arcId, expression);
    if (orientation.equals("PtoT") || orientation.equals("BOTHDIR")) {
      int place = vertexById.get(placeend);
      int trans = vertexById.get(transend);
      graph.get(place).add(new Arc(arcId, trans));
    }

    if (orientation.equals("TtoP") || orientation.equals("BOTHDIR")) {
      int place = vertexById.get(placeend);
      int trans = vertexById.get(transend);
      graph.get(trans).add(new Arc(arcId, place));
    }


  }


  public void combinePlaces(int a, int b) {
    assert typeByVertex.get(a) == VertexType.PLACE &&
      typeByVertex.get(b) == VertexType.PLACE;

    while (!graph.get(b).isEmpty()) {
      Arc arc = graph.get(b).get(graph.get(b).size() - 1);
      graph.get(a).add(new Arc(arc.getArcId(), arc.getTo()));
      graph.get(b).remove(graph.get(b).size() - 1);
    }
  }

  public void deleteTransition(int trans) {
    assert typeByVertex.get(trans) == VertexType.TRANSITION;

    graph.get(trans).clear();
  }

  public void deletePlace(int place) {
    assert typeByVertex.get(place) == VertexType.PLACE;

    graph.get(place).clear();
  }

  public void deleteArc(int a, int b) {
    for (int i = 0; i < graph.get(a).size(); i++) {
      if (graph.get(a).get(i).getTo() == b) {
        graph.get(a).remove(i);
        break;
      }
    }
  }

  public String getId(int vertex) {
    return idByVertex.get(vertex);
  }

  public int getVertexNumber() {
    return graph.size();
  }

  public boolean isStarted(int vertex) {
    return isStartedPlace.get(vertex);
  }

  public String getFusionInfo(int vertex) {
    return fusionInfoByVertex.get(vertex);
  }

  public String getExpression(String arcId) {
    return expressionByArcId.get(arcId);
  }

  public void setExpression(String arcId, String expression) {
    expressionByArcId.put(arcId, expression);
  }
}
