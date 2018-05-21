package iis.nsu.vishnevskii.cpn.change;

import org.w3c.dom.Document;

public class DeletePlaceChange extends Change {

  private String place1;

  public DeletePlaceChange(String place1) {
    this.place1 = place1;
  }

  @Override
  public void change(Document document) {
    System.out.println("Delete place: " + place1);
    deleteById(document, place1);
  }
}
