package org.multigraph;


/**
 * The Labeler class is responsible for choosing the positions of tick marks
 * and labels along an axis, and for rendering the labels.  Each Labeler instance
 * is associated with a particular Axis and label/tick spacing, as well as a DataFormatter
 * object for converting axis data values to strings, and positioning information
 * for the labels.
 * <p>
 * This is an abstract superclass.  The real work is done in DataType-specific
 * subclasses.
 */
public abstract class Labeler {
        
    protected Axis         mAxis;
    protected Point2D      mPosition;
    protected double       mAngle;
    protected Point2D      mAnchor;
    protected DataFormatter    mDataFormatter;

    /**
     * Protected constructor, only to be used in subclasses.
     */
    protected Labeler(Axis axis, DataFormatter dataFormatter, Point2D position, double angle, Point2D anchor) {
        mAxis         = axis;
        mPosition     = position;
        mAngle        = angle;
        mAnchor       = anchor;
        mDataFormatter    = dataFormatter;
    }

    /**
     * Return a number that measures how "dense" the labels generated
     * by this labeler would be along its axis.  The number represents
     * a ratio of the typical length of a label, divided by the
     * distance between labels (which is the same as the distance
     * between tick marks), both measured in pixels.  So a density near
     * 0 represent sparse labeling, and the density approaches 1 as
     * the labels fill more of the space along the axis.  A density of 1.0
     * corresponds to each label exactly touching
     * the next, and densities larger than 1.0 mean that labels
     * overlap.
     */
    public abstract double getLabelDensity();

    /**
     * Prepare this labeler for stepping along its axis between a given min and max value.
     * After calling this, you can call the hasNext(), next(), and peekNext() methods
     * to iterate through the values that this Labeler finds along its axis between this min
     * and max value.  These values will be spaced and aligned according to the spacing and
     * "start" values for this Labeler.
     */
    public abstract void prepare(DataValue min, DataValue max);

    /**
     * Indicate whether this Labeler can produce more values in the most recent
     * range passed to prepare().
     */
    public abstract boolean hasNext();

    /**
     * Return this Labeler's next value along the axis according to its spacing, but
     * do not update the internal counter to the next value.
     */
    public abstract DataValue peekNext();

    /**
     * Return this Labeler's next value along the axis according to its spacing, and
     * update the internal counter to the next value.
     */
    public abstract DataValue next();

    /**
     * Draw the label associated with a given DataValue along the axis
     */
    public abstract void renderLabel(DataValue value);
    
}
