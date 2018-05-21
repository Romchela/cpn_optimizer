package iis.nsu.vishnevskii.cpn.model;

import iis.nsu.vishnevskii.cpn.change.Change;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ChangesQueue {

  private Queue<Change> queue;

  public ChangesQueue() {
    queue = new LinkedList<>();
  }


  public void add(Change change) {
    queue.add(change);
  }

  public Change pop() {
    return queue.poll();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }
}
