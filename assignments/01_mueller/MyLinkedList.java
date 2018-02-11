/**
 * MyLinkedList implements a Linked List with simple links.
 * 
 * @author Christian Mueller, 1123410
 */

public class MyLinkedList<T extends Comparable<T>> {

    private int listSize;
    private Node<T> head;
    private Node<T> tail;

    // Default constructor
    public MyLinkedList() {

    }

    // appends the specified element to the end of this list.
    public void insert(T dataValue) {
        //list empty ?
        if (head == null) {
            head = new Node(dataValue);
            tail = head;
        } else {
            if (head == tail) {
                tail = new Node(dataValue);
                head.setNext(tail);

            } else {
                Node tmp = new Node(dataValue);
                tail.setNext(tmp);
                tail = tmp;
            }
        }

        listSize++;
    }
    //returns the first node with given dataValue
    public Node<T> search(T dataValue) {
        Node<T> currNode = head;
        Node<T> searchNode = new Node(dataValue);
        while (currNode != null) {
            if (currNode.compareTo(searchNode)==0)
                break;
            currNode = currNode.getNext();
        }
        return currNode;

    }

    // removes the element at the specified position in this list.
    public boolean delete(int index) {
        if (head != null) {
            // if the index is out of range, exit
            if (index < 0 || index > size())
                return false;
            if (index == 0) {
                head = head.getNext();
                return true;
            }
            Node<T> currNode = head;

            for (int i = 0; i < index - 1; i++)
                currNode = currNode.getNext();

            currNode.setNext(currNode.getNext().getNext());
            if (currNode.getNext() == null)
                tail = currNode;
            listSize--;
            return true;

        }
        return false;
    }

    // returns the element at the specified position in this list.
    public Comparable get(int index) {
        Node currNode = head;
        for (int i = 0; i < index; i++)
            currNode = currNode.getNext();

        return currNode.getData();
    }

    public Node<T> getNext(Node node) {
        return node.getNext();

    }

    public Node<T> getHead() {
        return head;
    }

    public int size() {
        return listSize;
    }

    public String toString() {
        String output = "";

        if (head != null) {
            Node currNode = head;
            while (currNode != null) {
                output += currNode.getData().toString();
                if (currNode.getNext() != null)
                    output += " ";
                currNode = currNode.getNext();
            }

        }
        return output;
    }

    //inner class representing the lists element

    public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
        // next node reference; null in case of last node
        Node<T> next;
        // data of the node
        T dataValue;

        public Node(T dataValue) {
            next = null;
            this.dataValue = dataValue;
        }

        public T getData() {
            return dataValue;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node nextValue) {
            next = nextValue;
        }

        @Override
        public int compareTo(Node<T> other) {
            return dataValue.compareTo(other.dataValue);
        }

    }
}
