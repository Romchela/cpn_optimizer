package iis.nsu.vishnevskii.cpn.model;

public class CPNVertex {

  public enum Type {
    TRANSITION, PLACE
  }

  private Type type;
  private int vertex;

  public CPNVertex(int vertex, Type type) {
    this.type = type;
    this.vertex = vertex;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public int getVertex() {
    return vertex;
  }

  public void setVertex(int vertex) {
    this.vertex = vertex;
  }
}
