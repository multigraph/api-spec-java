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
     * The DataValue.Type enum contains constants representing the various types of DataValue
     * possible in Multigraph; it has one constant for each DataValue subclass.
     */
    public enum Type {
    
        /**
         * A constant standing for the NumberValue type.
         */
        NUMBER("number"),

        /**
         * A constant standing for the DatetimeValue type.
         */
        DATETIME("datetime"),

        /**
         * A constant standing for an unspecified DatetimeValue type.
         */
        UNKNOWN("unknown");

        /**
         * Private constructor; used only within this file to create values of the enum listed above.
         */
        private Type(String v) {
            this.value = v;
        }

        /**
         * The string representation of this value.
         */
        private final String value;

        /**
         * Returns the string representation of an enum constant.  For example:
         * <p>
         *  System.out.printf("DataValue.Type.NUMBER = %s\n" , DataValue.Type.NUMBER.value());
         */
        public String value() {
            return this.value;
        }
    }


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
     * Create a new DataValue obtained by adding the given measure to this DataValue.
     */
    public abstract DataValue add(DataMeasure measure);

    /**
     * Convert a string to a DataValue.  The value returned will be a concrete
     * subclass of DataValue, depending on the indicated type.
     *
     * @param type the data type to return
     * @param string the string to convert
     */
    public static DataValue parse(DataValue.Type type, String string) {
        switch (type) {
        case NUMBER: return NumberValue.parse(string);
        case DATETIME: return DatetimeValue.parse(string);
        }
        return null;
    }

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
