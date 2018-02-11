/**
 *  Implementation of a the segment intersection algorithm ReportCuts.
 *
 *  Assignment 03, Advanced Algorithms & Data Structures, Summer term 2016.
 *  Department of Computer Sciences, University of Salzburg.
 *
 *  @author Daniel Kocher, 0926293
 */

import java.util.Scanner;
import java.util.regex.Pattern;

import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Arrays;

public class Segments {
  /**
   *  Main function.
   *  Processes the command-line arguments and calls the ReportCuts method.
   *
   *  @param args A List of 4-tuples each of which consists of 4 Double values
   */
  public static void main (String[] args) {
    Segments segments = new Segments();
    List<Point> s = readSegments();

    //System.out.println("Trivial Check: ");
    //String trivString = segments.trivialCheck(s); 
    //System.out.println(trivString);

    try {
      segments.reportCuts(s);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public Map<List<Point>, List<Point>> mapL = null;
  public Map<List<Point>, List<Point>> mapR = null;
  public Map<List<Point>, List<Point>> mapV = null;

  public Segments () {
    mapL = new HashMap<List<Point>, List<Point>>();
    mapR = new HashMap<List<Point>, List<Point>>();
    mapV = new HashMap<List<Point>, List<Point>>();
  }

  /**
   *
   */
  public static List<Point> readSegments () {
    Scanner scanner = new Scanner(System.in);
    List<Point> segments = new List<Point>(new Point.ComparatorX());
    Point[] associates = new Point[2];
    double[] values = new double[4];
    int idx = 0;
    while (scanner.hasNext()) {
      // parse values in line
      idx = 0;
      for (String s: scanner.nextLine().split("\\s")) {
        try {
          values[idx++] = Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
          nfe.printStackTrace();
        }
      }

      // x coordinates are equal => vertical segment
      if (values[0] == values[2]) { 
        segments.insert(new VSegment(values[0], values[1], values[2], values[3]));
        continue; 
      }

      // y coordinates are equal => horizontal segment
      associates[0] = new Point(values[0], values[1], null);
      associates[1] = new Point(values[2], values[3], associates[0]);
      associates[0].setAssociate(associates[1]);
      segments.insert(associates[0]);
      segments.insert(associates[1]);
    }
    
    return segments;
  }

  /**
   *
   */
  public String trivialCheck (List<Point> s) {
    java.util.List<Point> hs = new java.util.LinkedList<Point>();
    java.util.List<VSegment> vs = new java.util.LinkedList<VSegment>();

    List<Point>.Node<Point> current = s.getHead();
    while (current != null) {
      if (current.data instanceof VSegment) {
        vs.add((VSegment)current.data);
      } else {
        hs.add(current.data);
      }
        current = current.next;
    }

    java.util.List<double[]> reported = new java.util.LinkedList<double[]>();
    String ret = "";
    for (Point h: hs) {
      for (VSegment v: vs) {
        if (h.lowerX() <= v.x() && h.upperX() >= v.x() &&
            h.y() >= v.lowerY() && h.y() <= v.upperY())
        {
          double[] a = new double[] { v.x(), h.y() };
          boolean found = false;
          for (double[] p: reported) {
            if (p[0] == a[0] && p[1] == a[1]) {
              found = true;
              break;
            }
          }

          if (!found) {
            ret += v.x() + " " + h.y() + "\n";
            reported.add(a);
          }
        }
      }
    }

    return ret;
  }

  /**
   *  
   */
  public void reportCuts (List<Point> s) {
    if (s.size() <= 0) {
      return;
    }

    if (s.size() <= 1) {
      Point p = s.getHead().data;
      List<Point> l = new List<Point>(new Point.ComparatorY());
      l.insert(p);
      if (p instanceof VSegment) {
        mapV.put(s, l);
      } else if (p instanceof Point && p.x1 == p.lowerX()) {
        mapL.put(s, l);
      } else if (p instanceof Point && p.x1 == p.upperX()) {
        mapR.put(s, l);
      }
      return;
    }

    // split vertically
    List<Point> s1 = new List<Point>(new Point.ComparatorX());
    List<Point> s2 = new List<Point>(new Point.ComparatorX());
    int half = (s.size() % 2 == 0 ? (int)(s.size() / 2) : (int)(Math.ceil(s.size() / 2)));
    List<Point>.Node<Point> current = s.getHead();
    int counter = 0;
    while (current != null) {
      if (counter < half) {
        s1.insert(current.data);
      } else {
        s2.insert(current.data);
      }

      current = current.next;
      ++counter;
    }

    // recursive calls
    reportCuts(s1);
    reportCuts(s2);

    // report intersections
    List<Point> h1 = new List<Point>(new Point.ComparatorY());
    List<Point> v1 = new List<Point>(new Point.ComparatorY());
    h1.insert(mapR.get(s2));
    deleteIfAssociateExists(h1, mapL.get(s1));
    v1.insert(mapV.get(s1));
    intersectAndReport(h1, v1);
    
    List<Point> h2 = new List<Point>(new Point.ComparatorY());
    List<Point> v2 = new List<Point>(new Point.ComparatorY());
    h2.insert(mapL.get(s1));
    deleteIfAssociateExists(h2, mapR.get(s2));
    v2.insert(mapV.get(s2));
    intersectAndReport(h2, v2);
    
    // update L, R, V
    List<Point> newL = new List<Point>(new Point.ComparatorY());
    // first statement is equivalent to the following sequence of statements:
    // newL.insert(mapL.get(s1)); deleteIfAssociateExists(newL, mapR.get(s2));
    newL.insert(h2);
    newL.insert(mapL.get(s2));
    mapL.put(s, newL);

    List<Point> newR = new List<Point>(new Point.ComparatorY());
    // first statement is equivalent to the following sequence of statements:
    // newR.insert(mapR.get(s2)); deleteIfAssociateExists(newR, mapL.get(s1));
    newR.insert(h1);
    newR.insert(mapR.get(s1));
    mapR.put(s, newR);

    List<Point> newV = new List<Point>(new Point.ComparatorY());
    newV.insert(mapV.get(s1));
    newV.insert(mapV.get(s2));
    mapV.put(s, newV);
  }

  /**
   *
   */
  public void deleteIfAssociateExists (List<Point> s1, List<Point> s2) {
    if (s1 == null || s2 == null || s2.isEmpty()) {
      return;
    }

    List<Point>.Node<Point> current = s2.getHead();
    while (current != null) {
      if (s1.search(current.data.associate) != null) {
        s1.delete(current.data);
      }
      current = current.next;
    }
  }

  /**
   *
   */
  public void intersectAndReport (List<Point> h, List<Point> v) {
    if (h.isEmpty() || v.isEmpty()) {
      return;
    }

    List<Point>.Node<Point> currH = h.getHead();
    List<Point>.Node<Point> currV = v.getHead();
    List<Point>.Node<Point> tmpH = null;
    while (currV != null && currH != null) {
      if (currH.data.y() > ((VSegment)currV.data).upperY()) {
        currV = currV.next;
        continue;
      }

      if (currH.data.y() >= ((VSegment)currV.data).lowerY()) {
        System.out.println(currV.data.x() + " " + currH.data.y());

        tmpH = currH.next;
        while ( tmpH != null &&
                tmpH.data.y() < ((VSegment)currV.data).upperY())
        {
          System.out.println(currV.data.x() + " " + tmpH.data.y());
          tmpH = tmpH.next;
        }
       
        currV = currV.next;
      } else {
        if (currV != null) {
          currH = currH.next;
        }
      }
    }
  }
}
