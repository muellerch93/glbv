/**
 *
 */

import java.util.Comparator;

public class Point implements Comparable<Point> {
  public final double x1;
  public final double y1;
  public Point associate;

  /**
   *  Default constructor.
   */
  public Point (double x1, double y1, Point associate) {
    this.x1 = x1;
    this.y1 = y1;
    this.associate = associate;
  }

  /**
   *  Getter y coordinate.
   */
  public double x () {
    return x1;
  }

  /**
   *  Getter y coordinate.
   */
  public double y () {
    return y1;
  }

  public double lowerX () {
    return (x1 < associate.x1 ? x1 : associate.x1);
  }

  public double upperX () {
    return (x1 < associate.x1 ? associate.x1 : x1);
  }

  /**
   *  Setter associate.
   */
  public void setAssociate (Point associate) {
    this.associate = associate;
  }

  public boolean equals (Point other) {
    return (x1 == other.x1 && y1 == other.y1);
  }

  @Override
  public int compareTo (Point other) {
    // used to sort by y coordinate (y1 is the lower y coordiante of a VSegment)
    return (y1 == other.y1 ? 0 : (y1 < other.y1 ? (-1) : 1));
  }

  @Override
  public String toString () {
    return ("(" + x1 + ", " + y1 + ")");
  } 

  /**
   *  Comparator w.r.t. the x coordinate of a point.
   */
  public static class ComparatorX implements Comparator<Point> {
    @Override
    public int compare(Point p1, Point p2) {
      return (p1.x1 == p2.x1 ? 0 : (p1.x1 < p2.x1 ? (-1) : 1)); 
    }
  }

  /**
   *  Comparator w.r.t. the y coordinate of a point.
   */
  public static class ComparatorY implements Comparator<Point> {
    @Override
    public int compare(Point p1, Point p2) {
      return (p1.y1 == p2.y1 ? 0 : (p1.y1 < p2.y1 ? (-1) : 1)); 
    }
  }

}
