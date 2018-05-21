package iis.nsu.vishnevskii.cpn.optimizer;

import iis.nsu.vishnevskii.cpn.model.CPNNet;
import iis.nsu.vishnevskii.cpn.model.ChangesQueue;

public interface CPNOptimizer {

  CPNNet optimize(CPNNet net, ChangesQueue queue);
}
