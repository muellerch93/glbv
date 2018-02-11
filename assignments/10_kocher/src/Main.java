import java.util.PriorityQueue;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Main {
  public static void main (String[] args) {
    PriorityQueue<Entry<Double>> priorityQueue =
      new PriorityQueue<Entry<Double>>();
    FibonacciHeap<Entry<Double>> fibonacciHeap =
      new FibonacciHeap<Entry<Double>>();
    List<FibonacciHeapNode<Entry<Double>>> insertedNodes =
      new ArrayList<FibonacciHeapNode<Entry<Double>>>();
    Entry<Double> entry = null;
    
    // N offer
    int minN = ((int)Math.pow(10, 3));
    int maxN = ((int)Math.pow(10, 6));
    double key = 0.0;
    double data = 0.0;
    int randMin = 0;
    int randMax = maxN;
    Random random = new Random(42);
    int i = 0;
    Entry<Double> fibonacciHeapMin = null;
    Entry<Double> priorityQueueMin = null;
    FibonacciHeapNode<Entry<Double>> node = null;

    long timeFibonacciHeap = 0;
    long timePriorityQueue = 0;
    long start = 0;

    // add equals offer in terms of functionality (offer is wrapper around add
    // to satisfy the Queue interface) but is more efficient because it avoids
    // two additional wrapper function calls
    // therefore, we use add to evalute the performance
    
    // start tests
    
    // N offer
    fibonacciHeap = new FibonacciHeap<Entry<Double>>();
    priorityQueue = new PriorityQueue<Entry<Double>>();  
    System.out.println("++++++ 'N offer' test ++++++");
    for(int N = minN; N <= maxN; N *= 10) {
      timeFibonacciHeap = 0;
      timePriorityQueue = 0;

      System.out.print("+ N = " + N + ": ");
      for (i = 0; i < N; ++i) {
        key = ((double)random.nextInt(randMax - randMin + 1));
        data = ((double)random.nextInt(randMax - randMin + 1));
        entry = new Entry<Double>(key, data);

        start = System.currentTimeMillis();
        fibonacciHeap.add(new FibonacciHeapNode<Entry<Double>>(entry));
        timeFibonacciHeap += (System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        priorityQueue.offer(entry);
        timePriorityQueue += (System.currentTimeMillis() - start);
      }

      // simple check
      fibonacciHeapMin = fibonacciHeap.peek();
      priorityQueueMin = priorityQueue.peek();
      if (fibonacciHeapMin.compareTo(priorityQueueMin) != 0) {
        System.out.println("'N offer' test failed!");
        System.exit(-1);
      }

      System.out.println("FibonacciHeap (" + timeFibonacciHeap + "ms) is " +
        (timeFibonacciHeap < timePriorityQueue ? "faster" : "slower") +
        " than Java's PriorityQueue (" + timePriorityQueue + "ms)"
      );
    }
    
    
    // N/2 offer, N/2 poll
    fibonacciHeap = new FibonacciHeap<Entry<Double>>();
    priorityQueue = new PriorityQueue<Entry<Double>>();
    System.out.println("++++++ 'N/2 offer, N/2 poll' test ++++++");
    for(int N = minN; N <= maxN; N *= 10) {
      timeFibonacciHeap = 0;
      timePriorityQueue = 0;

      System.out.print("+ N = " + N + ": ");
      for (i = 0; i <= (N / 2); ++i) {
        key = ((double)random.nextInt(randMax - randMin + 1));
        data = ((double)random.nextInt(randMax - randMin + 1));
        entry = new Entry<Double>(key, data);

        start = System.currentTimeMillis();
        fibonacciHeap.add(new FibonacciHeapNode<Entry<Double>>(entry));
        timeFibonacciHeap += (System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        priorityQueue.offer(entry);
        timePriorityQueue += (System.currentTimeMillis() - start);
      }

      for (i = 0; i < (N / 2); ++i) {
        start = System.currentTimeMillis();
        fibonacciHeap.poll();
        timeFibonacciHeap += (System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        priorityQueue.poll();
        timePriorityQueue += (System.currentTimeMillis() - start);
      }

      // simple check
      fibonacciHeapMin = fibonacciHeap.peek();
      priorityQueueMin = priorityQueue.peek();
      if (fibonacciHeapMin.compareTo(priorityQueueMin) != 0) {
        System.out.println("'N/2 offer, N/2 poll' test failed! (" +
          fibonacciHeapMin.getKey() + " != " + priorityQueueMin.getKey() + ")"
        );
        System.exit(-1);
      }

      System.out.println("FibonacciHeap (" + timeFibonacciHeap + "ms) is " +
        (timeFibonacciHeap < timePriorityQueue ? "faster" : "slower") +
        " than Java's PriorityQueue (" + timePriorityQueue + "ms)"
      );
    }
    

    // N/2 offer, N/2 offer/poll alternating
    fibonacciHeap = new FibonacciHeap<Entry<Double>>();
    priorityQueue = new PriorityQueue<Entry<Double>>();
    System.out.println("++++++ 'N/2 offer, N/2 offer/poll alternating' test ++++++");
    for(int N = minN; N <= maxN; N *= 10) {
      timeFibonacciHeap = 0;
      timePriorityQueue = 0;

      System.out.print("+ N = " + N + ": ");
      for (i = 0; i <= (N / 2); ++i) {
        key = ((double)random.nextInt(randMax - randMin + 1));
        data = ((double)random.nextInt(randMax - randMin + 1));
        entry = new Entry<Double>(key, data);

        start = System.currentTimeMillis();
        fibonacciHeap.add(new FibonacciHeapNode<Entry<Double>>(entry));
        timeFibonacciHeap += (System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        priorityQueue.offer(entry);
        timePriorityQueue += (System.currentTimeMillis() - start);
      }

      for (i = 0; i <= (N / 2); ++i) {
        if (i % 2 == 0) {
          start = System.currentTimeMillis();
          fibonacciHeap.poll();
          timeFibonacciHeap += (System.currentTimeMillis() - start);

          start = System.currentTimeMillis();
          priorityQueue.poll();
          timePriorityQueue += (System.currentTimeMillis() - start);
        } else {
          key = ((double)random.nextInt(randMax - randMin + 1));
          data = ((double)random.nextInt(randMax - randMin + 1));
          entry = new Entry<Double>(key, data);

          start = System.currentTimeMillis();
          fibonacciHeap.add(new FibonacciHeapNode<Entry<Double>>(entry));
          timeFibonacciHeap += (System.currentTimeMillis() - start);

          start = System.currentTimeMillis();
          priorityQueue.offer(entry);
          timePriorityQueue += (System.currentTimeMillis() - start);
        }
      }

      // simple check
      fibonacciHeapMin = fibonacciHeap.peek();
      priorityQueueMin = priorityQueue.peek();
      if (fibonacciHeapMin.compareTo(priorityQueueMin) != 0) {
        System.out.println("'N/2 offer, N/2 offer/poll alternating' test failed!");
        System.exit(-1);
      }

      System.out.println("FibonacciHeap (" + timeFibonacciHeap + "ms) is " +
        (timeFibonacciHeap < timePriorityQueue ? "faster" : "slower") +
        " than Java's PriorityQueue (" + timePriorityQueue + "ms)"
      );
    }
     
    // 1000 offer, offer/poll/decreasekey/delete
    fibonacciHeap = new FibonacciHeap<Entry<Double>>();
    priorityQueue = new PriorityQueue<Entry<Double>>();
    insertedNodes = new ArrayList<FibonacciHeapNode<Entry<Double>>>();
    FibonacciHeapNode<Entry<Double>> minNode = null;
    System.out.println("++++++ '1000 offer, offer/poll/decreasekey/delete alternating' test ++++++");
    for(int N = minN; N <= maxN; N *= 10) {
      timeFibonacciHeap = 0;
      timePriorityQueue = 0;

      System.out.print("+ N = " + N + ": ");
      for (i = 0; i < 1000; ++i) {
        key = ((double)random.nextInt(randMax - randMin + 1));
        data = ((double)random.nextInt(randMax - randMin + 1));
        entry = new Entry<Double>(key, data);
        node = new FibonacciHeapNode<Entry<Double>>(entry);

        node = fibonacciHeap.add(node); // offer and add have equal functionality
        //priorityQueue.offer(entry);
        insertedNodes.add(node);

        if (minNode == null ||
            (minNode != null && minNode.compareTo(node) > 0))
        {
          minNode = node;
        }

        // simple check
        fibonacciHeapMin = fibonacciHeap.peek();
        if (fibonacciHeapMin.compareTo(minNode.entry) != 0) {
          System.out.println("'1000 offer, offer/poll/decreasekey/delete alternating' test failed! ");
          System.exit(-1);
        }
      }

      for (i = 0; i < 1000; ++i) {
        switch (i % 4) {
          case 0:
            key = ((double)random.nextInt(randMax - randMin + 1));
            data = ((double)random.nextInt(randMax - randMin + 1));
            entry = new Entry<Double>(key, data);
            node = new FibonacciHeapNode<Entry<Double>>(entry);

            start = System.currentTimeMillis();
            node = fibonacciHeap.add(node);
            timeFibonacciHeap += (System.currentTimeMillis() - start);
            insertedNodes.add(node);

            if (minNode == null ||
                (minNode != null && minNode.compareTo(node) > 0))
            {
              minNode = node;
            }

            // simple check
            fibonacciHeapMin = fibonacciHeap.peek();
            if (fibonacciHeapMin.compareTo(minNode.entry) != 0) {
              System.out.println("'1000 offer, offer/poll/decreasekey/delete alternating' test failed!");
              System.exit(-1);
            }

            break;
          case 1:
            start = System.currentTimeMillis();
            fibonacciHeapMin = fibonacciHeap.poll();
            timeFibonacciHeap += (System.currentTimeMillis() - start);

            // find new minimum for comparison
            node = insertedNodes.remove(insertedNodes.indexOf(minNode));
            //System.out.println("Polled " + fibonacciHeapMin.getKey() + ", " + node.entry.getKey() + ", " + minNode.entry.getKey() + ", " + (node.compareTo(minNode)));
            if (!insertedNodes.isEmpty()) {
              minNode = insertedNodes.get(0);
              for (FibonacciHeapNode<Entry<Double>> currentNode: insertedNodes)
              {
                if (minNode.compareTo(currentNode) > 0) {
                  minNode = currentNode;
                }
              }
            }

            // simple check
            fibonacciHeapMin = fibonacciHeap.peek();
            if (minNode.entry.compareTo(fibonacciHeapMin) != 0) {
              System.out.println("'1000 offer, offer/poll/decreasekey/delete alternating' test failed!");
              System.exit(-1);
            }

            break;
          case 2:
            node = insertedNodes.get(random.nextInt(insertedNodes.size()));
            //System.out.println("Decreasing " + entry.getKey() + " to " + (entry.getKey() - 2) + "\n" + fibonacciHeap);
            start = System.currentTimeMillis();
            node = fibonacciHeap.decreaseKey(node, node.entry.getKey() - 2);
            timeFibonacciHeap += (System.currentTimeMillis() - start);

            if (minNode == null ||
                (minNode != null && minNode.compareTo(node) > 0))
            {
              minNode = node;
            }

            // simple check
            fibonacciHeapMin = fibonacciHeap.peek();
            if (fibonacciHeapMin.compareTo(minNode.entry) != 0) {
              System.out.println("'1000 offer, offer/poll/decreasekey/delete alternating' test failed!");
              System.exit(-1);
            }

            break;
          case 3:
            node = insertedNodes.get(random.nextInt(insertedNodes.size()));

            // find new minimum for comparison
            // has to be done upfront since delete modifies node
            node = insertedNodes.remove(insertedNodes.indexOf(node));
            if (node == minNode && !insertedNodes.isEmpty()) {
              minNode = insertedNodes.get(0);
              for (FibonacciHeapNode<Entry<Double>> currentNode: insertedNodes)
              {
                if (minNode.compareTo(currentNode) > 0) {
                  minNode = currentNode;
                }
              }
            }

            //System.out.println(fibonacciHeap.size() + " Before delete(" + node.entry.getKey() + "): peek = " + fibonacciHeap.peek().getKey() + ", minNode = " + minNode.entry.getKey());
            start = System.currentTimeMillis();
            fibonacciHeapMin = fibonacciHeap.delete(node);
            timeFibonacciHeap += (System.currentTimeMillis() - start);
            //System.out.println(fibonacciHeap.size() + " After delete(" + node.entry.getKey() + "): min = " + fibonacciHeapMin.getKey() + ", minNode = " + minNode.entry.getKey());

            

            fibonacciHeapMin = fibonacciHeap.peek();
            if (fibonacciHeapMin.compareTo(minNode.entry) != 0) {
              System.out.println("'1000 offer, offer/poll/decreasekey/delete alternating' test failed!");
              System.exit(-1);
            }

            break;
        }
      }

      System.out.println("FibonacciHeap (" + timeFibonacciHeap + "ms)");
    }

    System.out.println("++++++ All tests passed! ++++++");
  }
}