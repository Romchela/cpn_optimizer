package iis.nsu.vishnevskii.cpn.change;

import org.w3c.dom.Document;

public class DeleteArcChange extends Change {

  private String arcId;

  public DeleteArcChange(String arcId) {
    this.arcId = arcId;
  }

  @Override
  public void change(Document document) {
    System.out.println("Delete arc: " + arcId);
    deleteById(document, arcId);
  }
}
