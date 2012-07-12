package org.multigraph;

/**
 * A DataMeasure object stores a "measure" which reprents a (directed)
 * distance along a number line.  DataMeasures can be added to DataValues
 * to obtain new DataValues; think of the DataMeasure as representing
 * a displacement of a certain distance (and direction) along the number line.
 * <p>
 * Multigraph uses DataMeasure instances to represent lengths along an
 * axis, for things like tick mark and label spacing, widths of bars
 * in bar graphs, etc.
 * <p>
 * The details of correctly measuring a length depend on the data
 * type.  For example, the measure of "one month" for the DataValue.Type.DATETIME
 * type is different depending on the month, since different months
 * have different lengths.  This abstract superclass exists so that
 * data-type-specific subclasses can implement whatever details are
 * needed for measurements of that data type.
 * <p>
 * Regardless of the specific underlying data type, every DataMeasure
 * corresponds to a particular real number that can generally be used
 * to represent its length along a real number axis corresponding to its
 * data type.  The getRealValue() method returns that number.  Note that this
 * number may not exactly represent the length of the measure in all cases (see
 * the comments about months above); it is intended to be the "typical",
 * or most common, length of the measure.  If you need to be precise, then
 * you'll have to work with the specific subclass.
 */
public abstract class DataMeasure {

    /**
     * Convert this DataMeasure to a real number that is commensurate with the real
     * number value of the underlying data type.  
     */
    public abstract double getRealValue();

    /**
     * Convert this DataMeasure to a string.
     */
    public abstract String toString();

    /**
     * Return the location of the first "tick" at or after a given value, in a grid of regularly
     * spaced ticks separated by this DataMeasure, and aligned with a given alignment.
     * More precisely, return
     * <pre>
     *     alignment + k * measure
     * </pre>
     * where <code>measure</code> corresponds to the measure represented by this DataMeasure,
     * and <code>k</code> is the smallest integer (inluding possibly negative integers) such
     * that <code>alignment + k * measure >= value</code>.
     */
    public abstract DataValue firstSpacingLocationAtOrAfter(DataValue value, DataValue alignment);

}
