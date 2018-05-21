package iis.nsu.vishnevskii.cpn;

import iis.nsu.vishnevskii.cpn.model.CPNNet;
import iis.nsu.vishnevskii.cpn.model.ChangesQueue;
import iis.nsu.vishnevskii.cpn.optimizer.CPNOptimizer;
import iis.nsu.vishnevskii.cpn.optimizer.ForkOptimizer;
import iis.nsu.vishnevskii.cpn.optimizer.MergerOptimizer;
import iis.nsu.vishnevskii.cpn.optimizer.UselessOptimizer;
import java.util.ArrayList;
import java.util.List;

public class CPNOptimizerProvider {

  private static List<CPNOptimizer> optimizerList;

  static {
    optimizerList = new ArrayList<>();

    optimizerList.add(new UselessOptimizer());
    optimizerList.add(new ForkOptimizer());
    optimizerList.add(new MergerOptimizer());
  }

  public CPNNet optimize(CPNNet net, ChangesQueue queue) {
    CPNNet result = new CPNNet();
    for (CPNOptimizer optimizer : optimizerList) {
      result = optimizer.optimize(net, queue);
    }
    return result;
  }
}
