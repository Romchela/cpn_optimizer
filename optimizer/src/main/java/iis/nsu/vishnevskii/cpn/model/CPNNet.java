package iis.nsu.vishnevskii.cpn.model;

import java.util.HashMap;
import java.util.Map;

public class CPNNet {

  private Map<String, CPNGraph> pages;

  public CPNNet() {
    pages = new HashMap<>();
  }

  public void addPage(String id, CPNGraph graph) {
    pages.put(id, graph);
  }

  public Map<String, CPNGraph> getPages() {
    return pages;
  }
}
