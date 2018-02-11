/**
 *  Main class implementing the merge algorithm(s) and in-/output.
 *
 *  Assignment 01, Advanced Algorithms & Data Structures, Summer term 2016.
 *  Department of Computer Sciences, University of Salzburg.
 *
 *  @author Christian Mueller, 1123410
 *  @author Daniel Kocher, 0926293
 */

import java.util.Scanner;

public class MergeLists {
  public static void main (String[] args) {
    List<Integer> l = new List<Integer>();
    List<Integer> m = new List<Integer>();
    Scanner scanner = new Scanner(System.in);

    for (String s: scanner.nextLine().split(" ")) {
      l.insert(Integer.parseInt(s));
    }

    for (String s: scanner.nextLine().split(" ")) {
      m.insert(Integer.parseInt(s));
    }

    /*
    for (int i = 0; i < 12; ++i) {
      l.delete(i);
      m.delete(i);
    }
    System.out.println(l.toString());
    System.out.println(m.toString());
    */

    List<Integer> n = merge(l, m);
    System.out.println(n.toString());
  }

  /**
   *  Needs constant additional memory (three references). Only the references
   *  of the nodes of the two lists are modified such that a the elements, which
   *  already exist, form the merged list.
   */
  public static <T extends Comparable<T>> List<T> merge(List<T> l, List<T> m) {
    // last element of the first list is smaller than the first element of the
    // second list => simply manipulate the references; no additional memory used
    if (l.getTail().compareTo(m.getHead()) <= 0) {
      l.getTail().next = m.getHead();
      l.setTail(m.getTail());
      return l;
    }

    // last element of the second list is smaller than the first element of the
    // first list => simply manipulate the references; no additional memory used
    if (m.getTail().compareTo(l.getHead()) <= 0) {
      m.getTail().next = l.getHead();
      m.setTail(l.getTail());
      return m;
    }

    // otherwise => general merge => needs three additional references = constant
    // amount of additional memory
    List<T>.Node<T> lcurrent = l.getHead();
    List<T>.Node<T> mcurrent = m.getHead();
    List<T>.Node<T> previous = null;

    while (lcurrent != null && mcurrent != null) {
      if (lcurrent.compareTo(mcurrent) <= 0) {
        while (lcurrent != null && lcurrent.compareTo(mcurrent) <= 0) {
          previous = lcurrent;
          lcurrent = lcurrent.next;
        }

        previous.next = mcurrent;
      } else if (mcurrent.compareTo(lcurrent) <= 0) {
        while (mcurrent != null && mcurrent.compareTo(lcurrent) <= 0) {
          previous = mcurrent;
          mcurrent = mcurrent.next;
        }

        previous.next = lcurrent;
      }
    }

    return (l.getHead().compareTo(m.getHead()) <= 0 ? l : m);
  }

  /**
   *  Needs (n + m) additional memory for two lists of size n and m, respectively.
   */
  public static <T extends Comparable<T>> List<T> merge2(List<T> l, List<T> m) {
    List<T> result = new List<T>();
    List<T>.Node<T> lcurrent = l.getHead();
    List<T>.Node<T> mcurrent = m.getHead();
    List<T>.Node<T> tmp = null;

    // insert in this specific case takes O(1) since the tail is checked upfront
    // in the insert method and we simply append it as new tail
    while (lcurrent != null || mcurrent != null) {
      if (lcurrent == null || mcurrent == null) {
        tmp = (lcurrent == null ? mcurrent : lcurrent);
        result.insert(tmp.data);
        tmp = tmp.next;
        
        if (lcurrent == null) {
          mcurrent = tmp;
        } else {
          lcurrent = tmp;
        }

        continue;
      }

      if (lcurrent.compareTo(mcurrent) >= 0) {
        result.insert(mcurrent.data);
        mcurrent = mcurrent.next;
      } else {
        result.insert(lcurrent.data);
        lcurrent = lcurrent.next;
      }
    }

    return result;
  }
}
