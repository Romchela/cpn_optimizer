package iis.nsu.vishnevskii.cpn.change;

import iis.nsu.vishnevskii.cpn.model.CPNVertex.Type;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReplaceToValueChange extends Change {

  private String arcId;
  private String newValue;
  private Type from;

  public ReplaceToValueChange(String arcId, String newValue, Type from) {
    this.arcId = arcId;
    this.newValue = newValue;
    this.from = from;
  }

  @Override
  public void change(Document document) {
    String tag = (from == Type.TRANSITION) ? "placeend" : "transend";
    NodeList list = document.getElementById(arcId).getElementsByTagName(tag);
    assert list.getLength() == 1;
    Node node = list.item(0);
    node.getAttributes().getNamedItem("idref").setNodeValue(newValue);
    System.out.println("Replace To Value\n" + "    arcId: " + arcId + "\n    newValue: " + newValue +
      "\n    from: " + from);
  }
}
