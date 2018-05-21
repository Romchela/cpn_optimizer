package iis.nsu.vishnevskii.cpn.change;

import iis.nsu.vishnevskii.cpn.ParserUtils;
import iis.nsu.vishnevskii.cpn.model.CPNVertex.Type;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReplaceExpressionChange extends Change {

  private String arcId;
  private String expression;

  public ReplaceExpressionChange(String arcId, String expression) {
    this.arcId = arcId;
    this.expression = expression;
  }

  @Override
  public void change(Document document) {
    Node arc = document.getElementById(arcId);
    Node annot = ParserUtils.getChildNode(arc, "annot");
    Node text = ParserUtils.getChildNode(annot, "text");
    //text.setNodeValue("ХУЙ");
  }
}
