package iis.nsu.vishnevskii.cpn.model;

public class Arc {
  private String arcId;
  private int to;

  public Arc(String arcId, int to) {
    this.arcId = arcId;
    this.to = to;
  }

  public String getArcId() {
    return arcId;
  }

  public int getTo() {
    return to;
  }

  public void setTo(int to) {
    this.to = to;
  }
}
