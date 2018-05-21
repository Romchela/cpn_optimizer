package iis.nsu.vishnevskii.cpn.change;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Change {

  public abstract void change(Document document);

  public void deleteById(Document document, String id) {
    Element element = document.getElementById(id);
    element.getParentNode().removeChild(element);
  }
}
