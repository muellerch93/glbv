import java.util.Collection;
import java.util.Queue;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class FibonacciHeap<E extends HeapEntry> extends AbstractQueue<E>
  implements Queue<E>
{
  private FibonacciHeapNode<E> min = null;
  private int size = 0;
  private Random random = null;

  public FibonacciHeap () {
    min = null;
    size = 0;
    random = new Random(42);
  }

  @Override
  public boolean add (final E newEntry) {
    add(new FibonacciHeapNode<E>(newEntry));
    return true;
  }

  public FibonacciHeapNode<E> add (final FibonacciHeapNode<E> newNode) {
    if (size <= 0 || min == null) {
      min = newNode;
      min.left = min;
      min.right = min;
      ++size;

      return newNode;
    }

    FibonacciHeapNode<E> right = min.right;
    min.right = newNode;
    newNode.left = min;
    right.left = newNode;
    newNode.right = right;

    if (Double.compare(newNode.entry.getKey(), min.entry.getKey()) < 0) {
      min = newNode;
    }
    ++size;

    return newNode;
  }

  @Override
  public boolean offer (final E entry) {
    return add(entry);
  }

  @Override
  public E peek () {
    return min.entry;
  }

  // deletemin()
  @Override
  public E poll () {
    FibonacciHeapNode<E> oldMin = min;

    //System.out.println("poll: size = " + size);

    if (size > 0) {
      // delete min from rootlist
      boolean isLastRoot = false;
      if (min.right == min) {
        //System.out.println("isLastRoot");
        isLastRoot = true;
      }

      FibonacciHeapNode<E> left = min.left;
      min.left.right = min.right;
      min.right.left = min.left;
      min = left;
      --size;

      if (isLastRoot) {
        min = null;
      }

      // insert children of (ex-)min into rootlist
      FibonacciHeapNode<E> firstChild = oldMin.child;
      FibonacciHeapNode<E> currentChild = firstChild;
      FibonacciHeapNode<E> right = null;
      if (currentChild != null) {
        do {
          currentChild.parent = null;
          currentChild.left = null;
          right = currentChild.right;
          currentChild.right = null;

          //System.out.println("Adding " + currentChild.getEntry().getKey() + " to rootlist");
          add(currentChild);

          currentChild = right;
        } while (currentChild != firstChild);
      }

      /*
      // set min pointer if rootlist is empty now
      if (isLastRoot) {
        min = firstChild;
      } else {
        min = min.getRight();
      }
      */

      //System.out.println("Minimum = " + min.getEntry().getKey());
      //System.out.println("Before consolidate():\n" + toString());

      if (size > 0 && min != null) {
        consolidate();
      }
    }

    //System.out.println(toString();

    return oldMin.entry;
  }

  private void consolidate () {
    int cArraySize = 2 * (int)Math.ceil(Math.log(size) / Math.log(2));
    List<FibonacciHeapNode<E>> cArray = new ArrayList<FibonacciHeapNode<E>>();

    // may be skipped due to default initialization
    for (int i = 0; i < cArraySize; ++i) {
      cArray.add(null);
    }

    //System.out.println("cArraySize = " + cArraySize);

    FibonacciHeapNode<E> currentNode = min;
    FibonacciHeapNode<E> lastNode = currentNode;
    FibonacciHeapNode<E> nodeToMerge = null;
    FibonacciHeapNode<E> right = null;
    do {
      //System.out.println("Consolidating " + currentNode.getEntry().getKey());

      // delete current node
      right = currentNode.right;
      currentNode.left = currentNode; // do not set to null but create circle
      currentNode.right = currentNode;

      while ((nodeToMerge = cArray.get(currentNode.degree)) != null) {
        //System.out.println("Merging " + currentNode.getEntry().getKey() + " (" + currentNode.getDegree() + ") and " + nodeToMerge.getEntry().getKey() + " (" + currentNode.getDegree() + ")");
        cArray.set(currentNode.degree, null);
        currentNode = link(currentNode, nodeToMerge);
        //System.out.println(toString(currentNode, 1));

        /*
        if (cArray.size() <= currentNode.degree {
          //System.out.println(currentNode.getEntry().getKey());
          FibonacciHeapNode<E> start = currentNode.child;
          FibonacciHeapNode<E> end = start.left;
          int count = 0;
          while (start != end) {
            ++count;
            start = start.getRight();
          }
          //System.out.println("Stored degree = " + currentNode.getDegree() + ", Real degree = " + count);
        }
        */
      }

      cArray.set(currentNode.degree, currentNode);
      currentNode = right;
    } while (currentNode != lastNode);

    min = null;
    for (FibonacciHeapNode<E> tree: cArray) {
      if (tree != null) {
        //System.out.println(">> " + toString(tree, 1));
        add(tree);
      }
    }

    //System.out.println("consolidate end: Min = " + min.getEntry().getKey());
  }

  public FibonacciHeapNode<E> link (
    FibonacciHeapNode<E> node1, FibonacciHeapNode<E> node2)
  {
    FibonacciHeapNode<E> tempMin = node1;
    FibonacciHeapNode<E> other = node2;
    FibonacciHeapNode<E> temp = null;
    
    if (tempMin.entry.getKey() > other.entry.getKey()) {
      temp = tempMin;
      tempMin = other;
      other = temp;
    }

    //System.out.println("linking " + tempMin.getEntry().getKey() + " (min) and " + other.getEntry().getKey());

    if (!tempMin.hasChild()) {
      //System.out.println(tempMin.getEntry().getKey() + " has no child");
      tempMin.child = other;
      other.parent = tempMin;
      tempMin.increaseDegree();
      return tempMin;
    }

    FibonacciHeapNode<E> child = tempMin.child;
    FibonacciHeapNode<E> right = child.right;
    child.right = other;
    other.left = child;
    right.left = other;
    other.right = right;
    other.parent = tempMin;
    tempMin.increaseDegree();

    return tempMin;
  }

  public FibonacciHeapNode<E> decreaseKey (FibonacciHeapNode<E> node,
    final double newKey)
  {
    final E entry = node.entry;
    if (newKey > entry.getKey()) {
      return node;
    }

    entry.setKey(newKey);

    if (!node.hasParent() || newKey >=  node.parent.entry.getKey()) {
      min = (newKey < min.entry.getKey() ? node : min);
      return node;
    }

    //System.out.println("decreaseKey: min = " + min.getEntry().getKey());
    FibonacciHeapNode<E> parent = null;
    do {
      parent = node.parent;
      cut(node);
      node = parent;
    } while (node.isMarked() && node.hasParent());
    //System.out.println("decreasKey: newMin = " + min.getEntry().getKey());

    min = (newKey < min.entry.getKey() ? node : min);

    if (node.hasParent()) {
      node.mark();
    }

    return node;
  }

  public void cut (final FibonacciHeapNode<E> toCut) {
    if (toCut.hasParent()) {
      FibonacciHeapNode<E> parent = toCut.parent;
      parent.decreaseDegree();
      toCut.parent = null;

      FibonacciHeapNode<E> right = toCut.right;
      FibonacciHeapNode<E> left = toCut.left;
      right.left = left;
      left.right = right;

      if (parent.child == toCut) {
        parent.child = (right == toCut ? null : right);
      }

      //System.out.println(this);
      add(toCut);
    }
  }

  @Override
  public boolean isEmpty () {
    return (size <= 0);
  }

  @Override
  public Iterator<E> iterator () {
    return null;
  }

  @Override
  public int size () {
    return size;
  }

  @Override
  public String toString () {
    return toString(min, 1);
  }

  public String toString (FibonacciHeapNode<E> root, int level) {
    if (root == null) {
      return "";
    }

    FibonacciHeapNode<E> start = root;
    FibonacciHeapNode<E> end = start;
    List<FibonacciHeapNode<E>> treeList = new ArrayList<FibonacciHeapNode<E>>();

    String str = "[" + level +
      (root.hasParent() ? ", " + root.parent.entry.getKey() : "") +
      "]";

    do {
      str += " <-> ";
      str += start.entry.getKey();

      //System.out.println(start.getEntry().getKey() + " has child? " + start.hasChild());
      if (start.hasChild()) {
        treeList.add(start);
      }

      //System.out.println(start.getEntry().getKey());

      start = start.right;
    } while (start != end);

    str += " (" + start.entry.getKey() + ")";

    for (FibonacciHeapNode<E> tree: treeList) {
      str += "\n";
      str += toString(tree.child, level + 1);
    }

    return str;
  }

  public E delete (final FibonacciHeapNode<E> node) {
    decreaseKey(node, Double.NEGATIVE_INFINITY);
    return poll();
  }
}