package iis.nsu.vishnevskii.cpn;


import iis.nsu.vishnevskii.cpn.change.Change;
import iis.nsu.vishnevskii.cpn.model.ChangesQueue;
import org.w3c.dom.Document;

public class OptimisationApplicator {

  public void apply(Document document, ChangesQueue queue) {
    while (!queue.isEmpty()) {
      Change change = queue.pop();
      change.change(document);
    }
  }
}
