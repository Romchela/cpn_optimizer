package iis.nsu.vishnevskii.cpn.io;

import iis.nsu.vishnevskii.cpn.model.CPNGraph;
import iis.nsu.vishnevskii.cpn.model.CPNNet;
import iis.nsu.vishnevskii.cpn.ParserUtils;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CPNParser {

  private String fileName;
  private Document document;

  public class DocumentAndNet {
    public Document document;
    public CPNNet net;

    public DocumentAndNet(Document document, CPNNet net) {
      this.document = document;
      this.net = net;
    }
  }

  public CPNParser(String fileName) {
    this.fileName = fileName;
  }

  public DocumentAndNet parse() {
    try {
      DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      document = documentBuilder.parse(fileName);
    } catch (SAXException | IOException | ParserConfigurationException e) {
      throw new RuntimeException("Parsing failed because of " + e.getMessage());
    }

    return new DocumentAndNet(document, buildGraph());
  }

  private String getMainPage() {
    NodeList items = document.getElementsByTagName("instances");
    return items.item(0).getChildNodes().item(1)
      .getAttributes().getNamedItem("id").getNodeValue();
  }

  private CPNNet buildGraph() {
    CPNNet net = new CPNNet();
    NodeList cpnetList = document.getElementsByTagName("cpnet");
    assert cpnetList.getLength() == 1;
    NodeList items = cpnetList.item(0).getChildNodes();

    String mainPage = getMainPage();

    for (int i = 0; i < items.getLength(); i++) {
      Node node = items.item(i);

      switch (node.getNodeName()) {
        case "globbox":
          break;

        case "page":
          String pageId = ParserUtils.getId(node);
          CPNGraph graph = parsePage(node, pageId);
          net.addPage(pageId, graph);
          break;
      }
    }

    return net;
  }

  private CPNGraph parsePage(Node node, String pageId) {
    NodeList children = node.getChildNodes();
    CPNGraph graph = new CPNGraph();
    for (int i = 0; i < children.getLength(); i++) {
      Node item = children.item(i);
      switch (item.getNodeName()) {
        case "place":
          String placeId = ParserUtils.getId(item);
          int initMarks = ParserUtils.getInitMarks(item);
          boolean isInState = ParserUtils.getInState(item);
          String fusionInfo = ParserUtils.getFusionInfo(item);
          graph.addPlace(placeId, (initMarks > 0) || isInState, pageId, fusionInfo);
          break;
        case "trans":
          String transId = ParserUtils.getId(item);

          String cond = ParserUtils.getCond(item);
          String code = ParserUtils.getCode(item);
          graph.addTrans(transId, cond, code, pageId);
          break;

        case "arc":
          String arcId = ParserUtils.getId(item);

          String orientation = ParserUtils.getOrientation(item);
          String transend = ParserUtils.getTransend(item);
          String placeend = ParserUtils.getPlaceend(item);
          String expression = ParserUtils.getExpression(item);

          graph.addArc(arcId, orientation, transend, placeend, expression);
          break;
      }
    }

    return graph;
  }

}
