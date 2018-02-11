public class Entry<E> implements HeapEntry, Comparable<Entry<E>> {
  public double key = 0.0;
  public E data = null;
  
  public Entry () {
    key = 0.0;
    data = null;
  }

  public Entry (final double key, final E data) {
    this.key = key;
    this.data = data;
  }

  @Override
  public double getKey () {
    return key;
  }

  @Override
  public void setKey (final double key) {
    this.key = key;
  }

  public E getData () {
    return data;
  }

  public void setData (final E data) {
    this.data = data;
  }

  @Override
  public String toString () {
    return ("[" + key + ": " + data + "]");
  }

  @Override
  public int compareTo (final Entry<E> other) {
    return Double.compare(key, other.getKey());
  }
}