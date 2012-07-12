package org.multigraph;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * A DatetimeMeasure stores a length of time, represented in terms of a "unit" and a "measure".
 * The unit is a calendar field value such as YEAR, MONTH, DAY, HOUR, etc, and the measure is
 * a floating point value that represents the number of units.
 */
public class DatetimeMeasure extends DataMeasure {

    /**
     * A DatetimeMeasure.Unit represents a field for calendar computations ---
     * year, month, day, hour, etc.  Multigraph uses its own enum for
     * this, rather than simply using the various field values in
     * java.util.Calendar, in order to have a convenient parse() method,
     * and to be able to associate a long integer with each field
     * representing the number of milliseconds it contains.
     *
     * So, for example, DatetimeMeasure.Unit.DAY.millisecs is the number
     * of milliseconds in a day.
     */
    public enum Unit {
        MILLISECOND("ms",      1L                             ),
        SECOND     ("s",       1000L                          ),
        MINUTE     ("m",       1000L * 60L                    ),                   
        HOUR       ("H",       1000L * 60L * 60L              ),             
        DAY        ("D",       1000L * 60L * 60L * 24L        ),       
        WEEK       ("W",       1000L * 60L * 60L * 24L * 7L   ),  
        MONTH      ("M",       1000L * 60L * 60L * 24L * 31L  ), // not always true, but that's OK
        YEAR       ("Y",       1000L * 60L * 60L * 24L * 365L ), // only true for non-leap years, but that's OK
        UNKNOWN    ("unknown", 0                              );

        /**
         * The string form of this field value; one of: "ms", "s", "m", "H", "D", "W", "M", "Y".
         */
        public final String value;

        /**
         * The number of milliseconds in one occurence of this calendar field.  For MONTH, the value
         * is the number of milliseconds in 31 days.  For YEAR, it is the number of milliseconds in 365 days.
         */
        public final long millisecs;
        private Unit(String v, long m) {
            value = v;
            millisecs = m;
        }

        /**
         * Return a DatetimeMeasure.Unit instance by parsing a string.  The string must be one
         * of the strings allowed for the <code>value</code> field.
         */
        public static DatetimeMeasure.Unit parse(String v) {
            for (DatetimeMeasure.Unit c: DatetimeMeasure.Unit.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }

        public static String toString(DatetimeMeasure.Unit u) {
            if (u==null) { return "UNKNOWN-CALENDAR-FIELD"; }
            return u.value;
        }
    }

    /**
     * The unit for this measure.
     */
    private DatetimeMeasure.Unit mUnit;

    /**
     * Return the unit for this DatetimeMeasure.
     */
    public DatetimeMeasure.Unit getUnit() { return mUnit; }
    
    /**
     * The amount of this measure.
     */
    private double mMeasure;

    /**
     * Return the measure for this DatetimeMeasure.
     */
    public double getMeasure() { return mMeasure; }

    /**
     * The number of milliseconds in this measure.  This is always equal to mMeasure * mUnit.millisecs.
     */
    private long mValue;

    /**
     * Construct a DatetimeMeasure instance using a given measure and unit.
     */
    public DatetimeMeasure(double measure, DatetimeMeasure.Unit unit) {
        mMeasure       = measure;
        mUnit          = unit;
        mValue         = (long)(measure * unit.millisecs);
    }

    /**
     * Construct a DatetimeMeasure instance by parsing a string.
     */
    public DatetimeMeasure(String string) {
        //TODO: Not Yet Implemented
    }

    /**
     * Convert this DatetimeMeasure to a floating point value on the same scale as a DatetimeValue.
     */
    @Override public double getRealValue() {
        return (double)mValue;
    }

    /**
     * Convert this DatetimeMeasure to a string.
     */
    @Override public String toString() {
        //TODO: Not Yet Implemented
        return null;
    }

    @Override public DataValue firstSpacingLocationAtOrAfter(DataValue value, DataValue alignment) {
        //TODO: Not Yet Implemented
        return null;
    }

}
