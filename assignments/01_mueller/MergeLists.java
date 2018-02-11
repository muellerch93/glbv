
import java.util.Scanner;

/**
 * MergeLists takes two sorted (ascending) lists as Input and merges them into a single sorted list.
 * 
 * @author Christian Mueller, 1123410
 */
public class MergeLists {

    public static void main(String[] args) {
        MyLinkedList[] sortedLists = readAndPrepareInput();
        MyLinkedList l3 = sortedListsMerge(sortedLists[0], sortedLists[1]);
        System.out.println(l3.toString());
       
    }

    public static <T extends Comparable<T>> MyLinkedList<T> sortedListsMerge(MyLinkedList<T> left,
            MyLinkedList<T> right) {
        MyLinkedList l3 = new MyLinkedList();

        MyLinkedList<T>.Node<T> leftElem = left.getHead();
        MyLinkedList<T>.Node<T> rightElem = right.getHead();

        /**
         * till the end of the shorter list is reached:
         * left > right: add right, next right
         * left = right: add right/left, next right/left 
         * left < right: add left, next left 
         */

        while (leftElem != null && rightElem != null) {
            switch (leftElem.compareTo(rightElem)) {
            case 0:
                l3.insert(leftElem.getData());
                leftElem = leftElem.getNext();
            case 1:
                l3.insert(rightElem.getData());
                rightElem = rightElem.getNext();  
                break;
            case -1:
                l3.insert(leftElem.getData());
                leftElem = leftElem.getNext();
            }
        }
        //take care of the remaining elements of the longer list
        MyLinkedList<T>.Node<T> cElem;
        cElem = (leftElem == null) ? rightElem : leftElem;

        while (cElem != null) {
            l3.insert(cElem.getData());
            cElem = cElem.next;
        }
        return l3;
    }

    @SuppressWarnings("resource")
    public static MyLinkedList[] readAndPrepareInput() {
        Scanner sc = new Scanner(System.in);
        MyLinkedList lists[] = new MyLinkedList[2];
        int listsScanned = 0;
        while (listsScanned < 2) {
            String in = sc.nextLine();
            String sortedListString[] = in.split("\\s+");
            lists[listsScanned] = new MyLinkedList();
            for (int i = 0; i < sortedListString.length; i++)
                lists[listsScanned].insert(Integer.parseInt(sortedListString[i]));
            listsScanned++;
        }
        return lists;

    }

}
