package iis.nsu.vishnevskii.cpn;

import iis.nsu.vishnevskii.cpn.io.CPNParser;
import iis.nsu.vishnevskii.cpn.io.CPNParser.DocumentAndNet;
import iis.nsu.vishnevskii.cpn.io.DocumentPrinter;
import iis.nsu.vishnevskii.cpn.model.ChangesQueue;
import org.w3c.dom.Document;

public class Main {

  public static final String FILENAME = "Par";//"atmr_2s";//"simple_chain";

  public static void main(String[] args) {

    CPNParser parser = new CPNParser(FILENAME + ".cpn");
    DocumentAndNet parsedData = parser.parse();

    Document document = parsedData.document;

    ChangesQueue queue = new ChangesQueue();
    CPNOptimizerProvider optimizer = new CPNOptimizerProvider();
    optimizer.optimize(parsedData.net, queue);

    OptimisationApplicator applicator = new OptimisationApplicator();
    applicator.apply(parsedData.document, queue);

    DocumentPrinter printer = new DocumentPrinter();
    printer.print(document, FILENAME + "_optimized.cpn");

  }
}
