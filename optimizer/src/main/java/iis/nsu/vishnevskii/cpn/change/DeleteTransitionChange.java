package iis.nsu.vishnevskii.cpn.change;

import org.w3c.dom.Document;

public class DeleteTransitionChange extends Change {

  private String trans;

  public DeleteTransitionChange(String trans) {
    this.trans = trans;
  }

  @Override
  public void change(Document document) {
    System.out.println("Delete transition: " + trans);
    deleteById(document, trans);
  }
}
