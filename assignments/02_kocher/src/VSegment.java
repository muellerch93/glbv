/**
 *
 */

public class VSegment extends Point {
  public final double x2;
  public final double y2;
  
  /**
   *  Default constructor.
   */
  public VSegment (double x1, double y1, double x2, double y2) {
    super(x1, (y1 > y2 ? y2 : y1), null);
    this.x2 = x2;
    this.y2 = (y1 > y2 ? y1 : y2);
  }

  /**
   *  Getter lower y coordinate.
   */
  public double lowerY () {
    return y1;
  }

  /**
   *  Getter upper y coordinate.
   */
  public double upperY () {
    return y2;
  }

  // compareTo modification not necessary since x1 and x2 are equal for vertical
  // segments.

  @Override
  public String toString () {
    return ("{" + super.toString() + ", (" + x2 + ", " + y2 + ")}");
  }
}
