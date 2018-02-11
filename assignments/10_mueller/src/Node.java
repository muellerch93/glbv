
public class Node<E extends HeapEntry> implements HeapEntry {

    E item;
    public Node<E> left;
    public Node<E> right;
    public Node<E> child;
    public Node<E> parent;

    public boolean isMarked;
    public int degree;

    public double key;

    Node(E item) {
	this.item = item;
	left = this;
	right = this;
    }

    @Override
    public double getKey() {

	return item.getKey();
    }

    @Override
    public void setKey(double key) {
	item.setKey(key);

    }

}
