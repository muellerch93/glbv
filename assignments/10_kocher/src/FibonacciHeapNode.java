public class FibonacciHeapNode<E extends HeapEntry> implements Comparable<FibonacciHeapNode<E>>
{
  public E entry = null;
  public FibonacciHeapNode<E> left = null;
  public FibonacciHeapNode<E> right = null;
  public FibonacciHeapNode<E> child = null;
  public FibonacciHeapNode<E> parent = null;
  public int degree = 0;
  public boolean marked = false;

  public FibonacciHeapNode () {
    entry = null;
    left = right = child = parent = null;
  }

  public FibonacciHeapNode (final E entry) {
    this.entry = entry;
    left = right = child = parent = null;
  }

  public FibonacciHeapNode (final E entry, final FibonacciHeapNode<E> left,
    final FibonacciHeapNode<E> right, final FibonacciHeapNode<E> child,
    final FibonacciHeapNode<E> parent)
  {
    this.entry = entry;
    this.left = left;
    this.right = right;
    this.child = child;
    this.parent = parent;
  }

  public E getEntry () {
    return entry;
  }

  public void setEntry (final E entry) {
    this.entry = entry;
  }

  public FibonacciHeapNode<E> getLeft () {
    return left;
  }

  public void setLeft (final FibonacciHeapNode<E> left) {
    this.left = left;
  }

  public FibonacciHeapNode<E> getRight () {
    return right;
  }

  public void setRight (final FibonacciHeapNode<E> right) {
    this.right = right;
  }

  public FibonacciHeapNode<E> getChild () {
    return child;
  }

  public void setChild (final FibonacciHeapNode<E> child) {
    this.child = child;
  }

  public FibonacciHeapNode<E> getParent () {
    return parent;
  }

  public void setParent (final FibonacciHeapNode<E> parent) {
    this.parent = parent;
  }

  public int getDegree () {
    return degree;
  }

  public void setDegree (final int degree) {
    this.degree = degree;
  }

  public boolean getMarked () {
    return marked;
  }

  public void setMarked (final boolean marked) {
    this.marked = marked;
  }

  public boolean isMarked () {
    return getMarked();
  }

  public void mark () {
    setMarked(true);
  }

  public void unmark () {
    setMarked(false);
  }

  public boolean hasChild () {
    return (getChild() != null);
  }

  public boolean hasParent () {
    return (getParent() != null);
  }

  public void increaseDegree () {
    ++degree;
  }

  public void decreaseDegree () {
    --degree;
  }

  @Override
  public String toString () {
    return "{ " +
      "entry: " + entry +
      ", left: " + (left == null ? "null" : left.getEntry()) +
      ", right: " + (right == null ? "null" : right.getEntry()) +
      ", child: " + (child == null ? "null" : child.getEntry()) +
      ", parent: " + (parent == null ? "null" : parent.getEntry()) +
      " }";
  }

  @Override
  public int compareTo (final FibonacciHeapNode<E> other) {
    return Double.compare(entry.getKey(), other.getEntry().getKey());
  }
}