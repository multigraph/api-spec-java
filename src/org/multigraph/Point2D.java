package org.multigraph;

/**
 * The Point2D object represents a point in 2-dimensional space, indicated by a pair of floating-point coordinate values.
 */
public class Point2D {

    /**
     * This point's coordinates.
     */
    private double x, y;

    /**
     * If this Point2D was created by parsing a string, these values store the original string representations of
     * the points' coordinates.  These are stored so that if/when the Point2D object is converted back to a string,
     * it will exactly match the originally parsed string.  These values remain null if a Point2D is not created
     * by parsing a string.
     */
    private String xString = null, yString = null;

    /**
     * Create a Point2D with the given coordinates.
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Create a Point2D by parsing a string.  The string should consists of a pair of floating point numbers
     * separated either by a comma, or by whitespace.
     */
    public static Point2D parse(String string) {
        String coords[] = string.split("[ ,]+");
        Point2D p = new Point2D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
        p.xString = coords[0];
        p.yString = coords[1];
        return p;
    }


    /**
     * Convert this Point2D to a string.
     */
    public String toString() {
        return (((xString != null) ? xString : Double.toString(x))
                + " " +
                ((yString != null) ? yString : Double.toString(y)));
    }

    /**
     * Return this Point2D's x coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * Return this Point2D's y coordinate.
     */
    public double getY() {
        return y;
    }

}
