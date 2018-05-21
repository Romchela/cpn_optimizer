package iis.nsu.vishnevskii.cpn;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParserUtils {

  public static String getId(Node node) {
    return node.getAttributes().getNamedItem("id").getNodeValue();
  }

  public static int getInitMarks(Node node) {
    try {
      Node initmark = getChildNode(node, "initmark");
      String text = getChildNode(initmark, "text").getTextContent();
      return Integer.valueOf(text.substring(0, text.indexOf('`')));
    } catch (Exception e) {
      return 0;
    }
  }

  public static String getFusionInfo(Node node) {
    try {
      Node fusioninfo = getChildNode(node, "fusioninfo");
      return fusioninfo.getAttributes().getNamedItem("name").getNodeValue();
    } catch (Exception e) {
      return null;
    }
  }

  public static boolean getInState(Node node) {
    try {
      String port = getChildNode(node, "port")
        .getAttributes()
        .getNamedItem("type")
        .getNodeValue();
      return port.equals("In");
    } catch (Exception e) {
      return false;
    }
  }

  public static Node getChildNode(Node node, String name) throws DOMException {
    NodeList children = node.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      Node item = children.item(i);
      if (item.getNodeName().equals(name)) {
        return item;
      }
    }

    throw new DOMException((short) 484, "Node " + name + " doesn't exist");
  }

  public static String getCode(Node node) throws DOMException {
    Node code = getChildNode(node, "code");
    Node text = getChildNode(code, "text");
    return text.getTextContent();
  }

  public static String getCond(Node node) throws DOMException {
    Node code = getChildNode(node, "cond");
    Node text = getChildNode(code, "text");
    return text.getTextContent();
  }

  public static String getTransend(Node node) {
    Node transend = getChildNode(node, "transend");
    return transend.getAttributes().getNamedItem("idref").getNodeValue();
  }

  public static String getPlaceend(Node node) {
    Node placeend = getChildNode(node, "placeend");
    return placeend.getAttributes().getNamedItem("idref").getNodeValue();
  }

  public static String getExpression(Node node) {
    Node annot = getChildNode(node, "annot");
    Node text = getChildNode(annot, "text");
    return text.getTextContent();
  }

  public static String getOrientation(Node node) {
    return node.getAttributes().getNamedItem("orientation").getNodeValue();
  }

}
