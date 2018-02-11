/**
 *  Implementation of a sorted singly linked list.
 *  Contains head and tail references, and the end of the list is represented
 *  as a reference to 'null'.
 *
 *  Assignment 01, Advanced Algorithms & Data Structures, Summer term 2016.
 *  Department of Computer Sciences, University of Salzburg.
 *
 *  @author Christian Mueller, 1123410
 *  @author Daniel Kocher, 0926293
 */

public class List<T extends Comparable<T>> {
  private Node<T> head = null;
  private Node<T> tail = null; // optimization

  public List () {
    head = null;
    tail = null;
  }

  /**
   *  Getter head.
   */
  public Node<T> getHead () {
    return head;
  }

  /**
   *  Getter tail.
   */
  public Node<T> getTail () {
    return tail;
  }

  /**
   *  Setter head.
   */
  public void setHead (Node<T> head) {
    this.head = head;
  }

  /**
   *  Setter tail.
   */
  public void setTail (Node<T> tail) {
    this.tail = tail;
  }

  /**
   *  Test whether the list is empty or not.
   *
   *  @return True if the list is empty, or false otherwise
   */
  public boolean isEmpty () {
    return (head == null);
  }

  /**
   *  Inserts a given element into the list and keeps the ordering.
   *
   *  @param element Element to insert
   */
  public void insert (T element) {
    if (element == null) {
      return;
    }
   
    Node<T> node = new Node<T>(element, null);
    
    // empty list, not iteration necessary
    if (isEmpty()) {
      head = node;
      tail = head;
      return;
    }
    
    // list contains n = 1 entry => no iteration necessary
    if (head.compareTo(node) >= 0) {
      head = new Node<T>(element, head);
      return;
    } 

    if (tail.compareTo(node) <= 0) {
      tail.next = node;
      tail = node;
      return;
    }

    // list contains n >= 2 entries (insert in between) => iterate
    Node<T> previous = null;
    Node<T> current = head;
    while (current != null && current.compareTo(node) < 0) {
      previous = current;
      current = current.next;
    }

    if (current != null) {
      node.next = current;
      previous.next = node;
    }
  }

  /**
   *  Deletes a given element from the list and keeps ordering.
   *
   *  @param element Element to delete
   */
  public void delete (T element) {
    if (element == null || head == null) {
      return;
    }

    Node<T> node = new Node<T>(element, null);

    // check head and tail upfront
    if (head.compareTo(node) > 0 || tail.compareTo(node) < 0) {
      return;
    }

    // deletion of the head
    if (head.compareTo(node) == 0) {
      head = head.next;
      return;
    }

    // deletion in between two elements
    Node<T> previous = null;
    Node<T> current = head;
    while (current != null && current.compareTo(node) != 0) {
      previous = current;
      current = current.next;
    }

    // element not found
    if (current == null) {
      return;
    }

    previous.next = current.next;

    // adjust tail if last element was removed
    if (previous.next == null) {
      tail = previous;
    }
  }

  /**
   *  Searches the list for a given element.
   *
   *  @param element Element to search for
   *
   *  @return The element if it has been found, or null otherwise
   */
  public Node<T> search (T element) {
    if (element == null || head == null) {
      return null;
    }

    Node<T> node = new Node<T>(element, null);

    // check head and tail upfront
    if (head.compareTo(node) > 0 || tail.compareTo(node) < 0) {
      return null;
    }

    if (head.compareTo(node) == 0) {
      return head;
    }

    if (tail.compareTo(node) == 0) {
      return tail;
    }
    
    Node<T> current = head;
    while (current != null) {
      if (current.compareTo(node) == 0) {
        return current;
      }

      current = current.next;
    }
    
    return null;
  }

  @Override
  public String toString () {
    String s = "";

    Node current = head;
    while (current != null) {
      s += current.data;
      if (current.next != null) {
        s += " ";
      }
      current = current.next;
    }

    return s;
  }

  /**
   *  Simple node class with a next pointer and a data field.
   */
  public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    public Node<T> next;
    public T data;

    public Node (T data, Node<T> next) {
      this.data = data;
      this.next = next;
    }

    @Override
    public int compareTo (Node<T> other) {
      return data.compareTo(other.data);
    }
  }
}
