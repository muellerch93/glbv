
public class FibonacciHeapNode implements HeapEntry, Comparable<FibonacciHeapNode> {

    public int entry;
    public double key;

    public FibonacciHeapNode(int entry, double key) {
	this.entry = entry;
	this.key = key;
    }

    public FibonacciHeapNode(double key) {
	this.entry = 0;
	this.key = key;
    }

    @Override
    public double getKey() {
	return key;
    }

    @Override
    public void setKey(double key) {
	this.key = key;
    }

    public String toString() {
	return key + ":" + entry;
    }

    @Override
    public int compareTo(FibonacciHeapNode other) {
	return Double.compare(this.getKey(), other.getKey());

    }

}
