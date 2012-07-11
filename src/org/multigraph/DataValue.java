package org.multigraph;

/**
 * A DataValue represents a data value that can be plotted along a
 * real number line.  There are different types of data values,
 * depending on the underlying data type (currently number and
 * datetime); this abstract superclass encapsulates operations
 * common to all data types.
 * <p>
 * Regardless of the specific underlying data type, every DataValue
 * corresponds to a particular real number that determines the
 * location of that value along a real number line for the purposes
 * of plotting.  The getRealValue() method returns that number.
 * <p>
 * This is an abstract superclass.  You can't create an instance of
 * this class directly; only of its subclasses.
 */
public abstract class DataValue {

    /**
     * Return the real number corresponding to this data value. Concrete subclasses
     * must override this.
     */
    public abstract double getRealValue();

    /**
     * Return a string representation of this DataValue.  This is just for debugging.
     * For pretty formatted output, use a DataFormatter instance.  Concrete subclasses
     * must override this.
     */
    public abstract String toString();

    /**
     * Comparator function. Concrete subclasses must override this.
     *
     * @param x The value to compare this value to
     * @return -1, 0, or 1, according as this DataValue is less than, equal to, or greater than x
     */
    public abstract int compareTo(DataValue x);
    
    /**
     * Less-than comparator function.  This function is implemented in terms of compareTo(); subclasses
     * do not need to override this function.
     *
     * @param x The value to compare this value to.
     * @return A boolean value indicating whether this value is less than x.
     */
    public boolean lt(DataValue x) { return compareTo(x) <  0; }

    /**
     * Less-than-or-equal comparator function.  This function is implemented in terms of compareTo(); subclasses
     * do not need to override this function.
     *
     * @param x The value to compare this value to.
     * @return A boolean value indicating whether this value is less than or equal to x.
     */
    public boolean le(DataValue x) { return compareTo(x) <= 0; }

    /**
     * Equal-to comparator function.  This function is implemented in terms of compareTo(); subclasses
     * do not need to override this function.
     *
     * @param x The value to compare this value to.
     * @return A boolean value indicating whether this value is equal to x.
     */
    public boolean eq(DataValue x) { return compareTo(x) == 0; }

    /**
     * Greater-than-or-equal comparator function.  This function is implemented in terms of compareTo(); subclasses
     * do not need to override this function.
     *
     * @param x The value to compare this value to.
     * @return A boolean value indicating whether this value is greater than or equal to x.
     */
    public boolean ge(DataValue x) { return compareTo(x) >= 0; }

    /**
     * Greater-than comparator function.  This function is implemented in terms of compareTo(); subclasses
     * do not need to override this function.
     *
     * @param x The value to compare this value to.
     * @return A boolean value indicating whether this value is greater than x.
     */
    public boolean gt(DataValue x) { return compareTo(x) >  0; }
    
}
