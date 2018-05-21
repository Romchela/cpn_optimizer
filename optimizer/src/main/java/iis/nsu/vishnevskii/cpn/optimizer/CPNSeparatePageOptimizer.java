package iis.nsu.vishnevskii.cpn.optimizer;

import iis.nsu.vishnevskii.cpn.model.CPNGraph;
import iis.nsu.vishnevskii.cpn.model.CPNNet;
import iis.nsu.vishnevskii.cpn.model.ChangesQueue;
import java.util.Map;

public abstract class CPNSeparatePageOptimizer implements CPNOptimizer {

  abstract CPNGraph optimizePage(CPNGraph graph, ChangesQueue queue);

  @Override
  public CPNNet optimize(CPNNet net, ChangesQueue queue) {
    CPNNet optimized = new CPNNet();
    int flag = 0;
    for (Map.Entry<String, CPNGraph> entry : net.getPages().entrySet()) {
      if (flag < 1000) {
        optimized.addPage(entry.getKey(), optimizePage(entry.getValue(), queue));
        flag++;
      } else {
        optimized.addPage(entry.getKey(), entry.getValue());
      }
    }
    return optimized;
  }
}
