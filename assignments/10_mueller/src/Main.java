
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
	AbstractQueue<FibonacciHeapNode> queue;

	int n = (int) Math.pow(10, 3);

	System.out.println("n offer");
	simulateInsert(new FibonacciHeap<FibonacciHeapNode>(), n, "Fibonacci Heap");
	simulateInsert(new PriorityQueue<FibonacciHeapNode>(), n, "Priority Queue");

	System.out.println("n poll");
	simulatePoll(new FibonacciHeap<FibonacciHeapNode>(), n, "Fibonacci Heap");
	simulatePoll(new PriorityQueue<FibonacciHeapNode>(), n, "Priority Queue");

	System.out.println("n/2 offer; n/2 poll");
	simulateInsertPoll(new FibonacciHeap<FibonacciHeapNode>(), n, "Fibonacci Heap");
	simulateInsertPoll(new PriorityQueue<FibonacciHeapNode>(), n, "Priority Queue");

	System.out.println("n/2 offer; n/2 alternate offer and poll");
	simulateAlternateInsertPoll(new FibonacciHeap<FibonacciHeapNode>(), n, "Fibonacci Heap");
	simulateAlternateInsertPoll(new PriorityQueue<FibonacciHeapNode>(), n, "Priority Queue");

	System.out.println("All");
	simulateAll(new FibonacciHeap<FibonacciHeapNode>(), n, "Fibonacci Heap");

	/**System.out.println("n/2 offer, n/2 poll");
	System.out.print("Fibonacci Heap: ");
	//FibHeap
	fibHeap = new FibonacciHeap<FibonacciHeapNode>();
	start = System.currentTimeMillis();
	for (int i = 0; i < n / 2; i++)
	    fibHeap.offer(randomHeapNodes[i]);
	for (int i = 0; i < n / 2; i++)
	    fibHeap.poll();
	System.out.println(System.currentTimeMillis() - start);
	//Priority Queue
	System.out.print("Priority Queue: ");
	prioQueue = new PriorityQueue<Integer>();
	start = System.currentTimeMillis();
	for (int i = 0; i < n / 2; i++)
	    prioQueue.offer(randomIntegers[i]);
	for (int i = 0; i < n / 2; i++)
	    prioQueue.poll();
	System.out.println(System.currentTimeMillis() - start);
	System.out.println();
	
	System.out.println("n/2 offer, n/2 alternate offer and poll");
	System.out.print("Fibonacci Heap: ");
	fibHeap = new FibonacciHeap<FibonacciHeapNode>();
	start = System.currentTimeMillis();
	for (int i = 0; i < n / 2; i++)
	    fibHeap.offer(randomHeapNodes[i]);
	for (int i = 0; i < n / 2; i++) {
	    switch (i % 2) {
	    case 0:
		fibHeap.offer(randomHeapNodes[n - i - 1]);
		break;
	    case 1:
		fibHeap.poll();
		break;
		}
	}
	System.out.println(System.currentTimeMillis()-start);
	System.out.print("PriorityQueue: ");
	prioQueue = new PriorityQueue<Integer>();
	start = System.currentTimeMillis();
	for (int i = 0; i < n / 2; i++)
	    prioQueue.offer(randomIntegers[i]);
	for (int i = 0; i < n / 2; i++) {
	    switch (i % 2) {
	    case 0:
		prioQueue.offer(randomIntegers[n - i - 1]);
		break;
	    case 1:
		prioQueue.poll();
		break;
		}
	}
	System.out.println(System.currentTimeMillis()-start);**/
    }

    public static void simulateInsert(AbstractQueue queue, int n, String dataStruct) {
	Random rand = new Random();
	FibonacciHeapNode randomHeapNodes[] = new FibonacciHeapNode[n];
	for (int i = 0; i < n; i++)
	    randomHeapNodes[i] = new FibonacciHeapNode(rand.nextInt());

	System.out.print(dataStruct + ": ");
	long start = System.currentTimeMillis();
	for (int i = 0; i < n; i++)
	    queue.offer(randomHeapNodes[i]);
	System.out.println(System.currentTimeMillis() - start);
    }

    public static void simulatePoll(AbstractQueue queue, int n, String dataStruct) {
	Random rand = new Random();
	FibonacciHeapNode randomHeapNodes[] = new FibonacciHeapNode[n];
	for (int i = 0; i < n; i++) {
	    randomHeapNodes[i] = new FibonacciHeapNode(rand.nextInt());
	    queue.offer(randomHeapNodes[i]);
	}
	System.out.print(dataStruct + ": ");
	long start = System.currentTimeMillis();
	for (int i = 0; i < n; i++)
	    queue.poll();
	System.out.println(System.currentTimeMillis() - start);

    }

    public static void simulateInsertPoll(AbstractQueue queue, int n, String dataStruct) {
	Random rand = new Random();
	FibonacciHeapNode randomHeapNodes[] = new FibonacciHeapNode[n];
	for (int i = 0; i < n; i++)
	    randomHeapNodes[i] = new FibonacciHeapNode(rand.nextInt());

	System.out.print(dataStruct + ": ");
	long start = System.currentTimeMillis();
	for (int i = 0; i < n / 2; i++)
	    queue.offer(randomHeapNodes[i]);
	for (int i = 0; i < n / 2; i++)
	    queue.poll();
	System.out.println(System.currentTimeMillis() - start);
    }

    public static void simulateAlternateInsertPoll(AbstractQueue queue, int n, String dataStruct) {
	Random rand = new Random();
	FibonacciHeapNode randomHeapNodes[] = new FibonacciHeapNode[n];
	for (int i = 0; i < n; i++)
	    randomHeapNodes[i] = new FibonacciHeapNode(rand.nextInt());

	System.out.print(dataStruct + ": ");
	long start = System.currentTimeMillis();
	for (int i = 0; i < n / 2; i++)
	    queue.offer(randomHeapNodes[i]);
	for (int i = 0; i < n / 2; i++) {
	    switch (i % 2) {
	    case 0:
		queue.offer(randomHeapNodes[n - i - 1]);
		break;
	    case 1:
		queue.poll();
		break;
	    }
	}
	System.out.println(System.currentTimeMillis() - start);
    }

    public static void simulateAll(FibonacciHeap<FibonacciHeapNode> queue, int n, String dataStruct) {
	Random rand = new Random();
	FibonacciHeapNode randomHeapNodes[] = new FibonacciHeapNode[2*n];
	for (int i = 0; i < 2*n; i++)
	    randomHeapNodes[i] = new FibonacciHeapNode(i);
	ArrayList<Node<FibonacciHeapNode>> insertedNodes = new ArrayList<Node<FibonacciHeapNode>>();
	ArrayList<Boolean> nodeDeleted = new ArrayList<Boolean>();
	
	//System.out.print(dataStruct + ": ");
	
	long start = System.currentTimeMillis();
	for (int i = 0; i < 2*n; i++)
	    insertedNodes.add(queue.insert(randomHeapNodes[i]));
	System.out.println("Size: "+queue.size);
	for (int i = 0; i < n; i++) {
//	    switch (i % 4) {
//	    case 0:
//		System.out.println("Insert Element: ");
//		insertedNodes.add(queue.insert(randomHeapNodes[n - i - 1]));
//		break;
//	    case 1:
//		System.out.println("Remove Min: ");
//		Node<FibonacciHeapNode> oldMin = queue.getMin();
//		queue.poll();
//		insertedNodes.remove(oldMin);
//		break;
//	    case 2:
//		System.out.println("Decrease Key: ");
//		int pos=rand.nextInt(insertedNodes.size());
//		Node<FibonacciHeapNode> decNode=insertedNodes.get(pos);
//		queue.decreaseKey(decNode,decNode.getKey()-10);
//		break;
//	    case 3:
	    	
		Node<FibonacciHeapNode> delNode=insertedNodes.get(rand.nextInt(insertedNodes.size()));
		//System.out.println("delete: "+delNode.getKey());
		queue.delete(delNode);
		insertedNodes.remove(delNode);

//	    }
	   
	    //printArrayList(insertedNodes);
	    //System.out.println("--------------");
	}
	System.out.println(System.currentTimeMillis() - start);

    }

    public static void printArrayList(ArrayList<Node<FibonacciHeapNode>> arrayList) {
	for (int i = 0; i < arrayList.size(); i++) {
	    System.out.println(arrayList.get(i).item);
	}

    }

}
