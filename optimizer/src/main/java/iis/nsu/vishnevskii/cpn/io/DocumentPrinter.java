package iis.nsu.vishnevskii.cpn.io;

import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class DocumentPrinter {

  public void print(Document document, String fileName) {
    try {
      Transformer tr = TransformerFactory.newInstance().newTransformer();
      DOMSource source = new DOMSource(document);
      FileOutputStream fos = new FileOutputStream(fileName);
      StreamResult result = new StreamResult(fos);
      tr.transform(source, result);
    } catch (TransformerException | IOException e) {
      throw new RuntimeException("DocumentPrinter failed because of " + e.getMessage());
    }
  }

}
