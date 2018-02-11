import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Iterator;

public class FibonacciHeap<E extends HeapEntry> extends AbstractQueue<E> {
    Node<E> min;
    int size;

    /**
     * Inserts new object into fibonacci heap.
     * @param object to be inserted into fibonacci heap
     * @return  the node encapsulating the newly inserted data
     */
    public Node<E> insert(E e) {
			Node<E> newNode = new Node<E>(e);
			if (min == null)
			    min = newNode;
			else
			    min = mergeLists(min, newNode);
			size++;
			return newNode;
    }

    /**
    * Returns object with the smallest key.
    * @return object with the smallest key
    */
    @Override
    public E peek() {
			return (E) min.item;
    }

    /**
     * Returns node that encapsulates object with the smallest key.
     * @return node encapsulating the object with the smallest key
     */
    //get the node with the smallest key
    public Node<E> getMin() {
			return min;
    }

    /**
     * Inserts new object into fibonacci heap.
     * @param e object to be inserted into fibonacci heap
     */
    @Override
    public boolean offer(E e) {
			if (min == null)
			    min = new Node<E>(e);
			else {
			    min = mergeLists(min, new Node<E>(e));
			}
			size++;
			return true;
    }

    /**
    * Returns and deletes node encapsulating object with the smallest key.
    */
    @Override
    public E poll() {
			Node<E> m = min;
			
			if (min == null)
			    return null;
			if (min.right == min) {
			    min = null;
			    //System.out.println("is alone: " + m + ", " + m.child);
			} else {
			    // Delete minimum from rootlist
			    min.left.right = min.right;
			    min.right.left = min.left;
			    // not really the new  minimum; just a pointer to random element of the
			    // rootlist
			    Node<E> end = min.right, start = end.right;
			    Node<E> minTmp = end;
			    while (start != end) {
			    	if (start.getKey() < minTmp.getKey())
			    		minTmp = start;
			    	start = start.right;
			    }
			    min = minTmp;
			}

			// add all the children of old minimum into the rootlist
			// prepare children of old minimum by removing their parent pointer;
			// iterate over children till you see the child of the old min
			// again
			if (m.child != null) {
			    Node<E> c = m.child.right;
			    while (c != m.child) {
						c.parent = null;
						c = c.right;
			    }
			    // remove the parent pointer of child node
			    c.parent = null;
			}
			//System.out.println("Poll: Minimum = " + min + ", " + m + ", " + m.child);
			// now add all the children of old minimum into the rootlist
			if (min != null && m.child != null) {
				min = mergeLists(min, m.child);
				consolidate();
			}
			size--;
			// we do not want to return the current minimum of the heap but the
			// removed one
			return m.item;
    }

    /**
     * Ensures that no two roots (trees) in the fibonacci have the same degree.
     */
    private void consolidate() {
			//System.out.println("Consolidate");
			// visit all the elements of the rootlist
			ArrayList<Node<E>> a = new ArrayList<Node<E>>();
			int sizeOfColArray = (int) Math.ceil(2 * (Math.log10(size) / Math.log10(2)));

			for (int i = 0; i < sizeOfColArray; i++)
			    a.add(null);
			//get root list
			ArrayList<Node<E>> toVisit = toList(min);

			//touch every element of the root list once
			for (int i = 0; i < toVisit.size(); i++) {
			    Node<E> c = toVisit.get(i);
			    // as long as there exists a tree with the same degree as the
			    // current degree; merge those trees
			    while (true) {
						if (a.get(c.degree) == null) {
				    	a.set(c.degree, c);
				    	break;
						}
						// we already have a tree of this degree => merge the trees
						Node<E> old = a.get(c.degree);
						// free position
						a.set(c.degree, null);
						// now we have to link the two trees that share the same degree
						// (old and c)
						c = link(c, old);
			    }
			    //find new minimum of fibonacci heap;
			    if (c.getKey() <= min.getKey())
						min = c;
			}
    }

    /**
     * Set the key of the specified node to a new value smaller than its old value.
     * @param node 
     * @param newKey new key of specified node
     */
    public void decreaseKey(Node<E> node, double newKey) {
			if (node.getKey() <= newKey )
			    return;
			node.setKey(newKey);
			//update minimum if needed
			if (node.getKey() < min.getKey())
			    min = node;
			//System.out.println("decreaseKey: Minimum = " + min.getKey());
			// if the decreased node is part of the rootlist or the decrease of the
			// key left the decreased node bigger than its parent => do nothing
			if (node.parent == null || newKey >= node.parent.getKey())
			    return;
			do {
			    Node<E> parent = node.parent;
			    cut(node);
			    // move one up
			    node = parent;
			} while (node.isMarked && node.parent != null);
			//System.out.println("decreaseKey: Min node = "+min+", "+min.child);
			// mark the parent of cutted node if its not in the rootlist (just lost
			// a son)
			if (node.parent != null)
		    node.isMarked = true;
	    }

		public void delete(Node<E> node) {
			//System.out.println("delete 1: Minimum = " + min + ", " + min.getKey());
			if (node.getKey() != min.getKey())
				decreaseKey(node, Double.NEGATIVE_INFINITY);

			//System.out.println("delete 2: Minimum = " + min + ", " + min.getKey());
			poll();
			//System.out.println("delete 3: Minimum = " + min + ", " + min.getKey());
    }

    public void cut(Node<E> node) {
			node.isMarked = false;
			
			if (node.parent != null) {
			    //parent loses one son
			    node.parent.degree--;
			   
			    if (node.parent.child == node)
						node.parent.child = (node.right != node) ? node.right : null;
			    
			    // delete the node we want to cut from the child list of its parent
			    node.left.right = node.right;
			    node.right.left = node.left;
			   
			    node.right = node;
			    node.left = node;
			    //node will be added into the rootlist therfore delete its parent
			    node.parent = null;

			    // add the cutted node to the root list
			    min = mergeLists(min, node);
			    
			    //System.out.println("Min node: "+min);
			}
    }

    /**
    * Merge to unsorted double linked ciruclar lists. 
    * @param first
    * @param second
    * @return
    */
    private Node<E> mergeLists(Node<E> first, Node<E> second) {
			if (first == null && second == null)
			    return null;
			if (first == null && second != null)
			    return second;
			if (first != null && second == null)
			    return first;

			Node<E> cNext = first.right;
			first.right = second.right;
			first.right.left = first;
			second.right = cNext;
			second.right.left = second;

			return first.getKey() < second.getKey() ? first : second;
    }

    // links to trees (both roots are part of the root list*) of same degree by
    // appending the bigger root to the smaller on (as a child)
    // * also takes care of the obvious necessity to remove the root with the
    // bigger key from the rootlist of the fibonacci heap
    public Node<E> link(Node<E> first, Node<E> second) {
			// determine which tree has the smaller root;
			Node<E> smallerRoot, biggerRoot;
			if (first.getKey() < second.getKey()) {
			    smallerRoot = first;
			    biggerRoot = second;
			} else {
			    smallerRoot = second;
			    biggerRoot = first;
			}
			// remove the biggerRoot from the rootlist
			biggerRoot.left.right = biggerRoot.right;
			biggerRoot.right.left = biggerRoot.left;
			biggerRoot.right = biggerRoot;
			biggerRoot.left = biggerRoot;

			// append the bigger root to the child of the smaller root
			smallerRoot.child = mergeLists(smallerRoot.child, biggerRoot);
			// bigger Root is now the child of smaller root => set parent of
			// biggerRoot
			biggerRoot.parent = smallerRoot;
			biggerRoot.isMarked = false;
			smallerRoot.isMarked = false;

			smallerRoot.degree++;
			return smallerRoot;
    }

    public ArrayList<Node<E>> toList(Node<E> start) {
			// Use ArrayList to deal with resizing.
			ArrayList<Node<E>> al = new ArrayList<Node<E>>();
			Node<E> currentElement = start.right;
			while (currentElement != start) {
			    al.add(currentElement);
			    currentElement = currentElement.right;
			}
			al.add(currentElement);
			return al;
    }

    @Override
    public int size() {
			// TODO Auto-generated method stub
			return size;
    }

    @Override
    public Iterator<E> iterator() {
			// TODO Auto-generated method stub
			return null;
    }

}
