package org.multigraph;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * DatetimeValue is the DataValue implementation for the DATETIME data type (DataValue.Type.DATETIME).
 * It represents a moment in time, in the UTC (GMT) time zone, with
 * milliscond precision.  The value is stored internally as a double
 * that represents the number of milliseconds since the epoch (i.e., the Unix timestamp
 * value, but stored as a double rather than an integer).
 */
public class DatetimeValue extends DataValue {

    /**
     * The value of this DatetimeValue instance -- number of milliseconds since the epoch.
     */
    protected double mValue;

    /**
     * Create a new DatetimeValue object with the given double value.
     */
    public DatetimeValue(double value) {
        this.mValue = value;
    }

    /**
     * Create a new DatetimeValue object by parsing a string
     */
    public DatetimeValue(java.lang.String value) {
        try {
            SimpleDateFormat sdf = mFormatters.get(value.length());
            if (sdf == null) {
                sdf = mFormatters.get(16);
            }
            this.mValue = sdf.parse(value).getTime();
        } catch (Exception e) {
            this.mValue = 0;
        }
    }

    @Override
        public double getRealValue() {
        return mValue;
    }

    /**
     * Return a java.util.Date instance representing the same moment in time
     * as this DatetimeValue.
     */
    public Date getDateValue() {
        return new Date((long)mValue);
    }

    @Override
        public java.lang.String toString() {
        return mFormatters.get(16).format(new Date((long)mValue));
    }

    /**
     * Comparison function.
     * @param  x Another DatetimeValue
     * @return -1, 0, or 1, according as this value is less, equal, or greater than x.
     */
    @Override
        public int compareTo(DataValue x) {
        return (new Double(mValue)).compareTo(((DatetimeValue)x).mValue); 
    }

    /**
     * An internal reference to the UTC time zone object.
     */
    private static TimeZone mUTCTimeZone = TimeZone.getTimeZone("GMT");

    /**
     * A private internal class used to store a static collection of
     * SimpleDateFormat instances used for parsing. The purpose of
     * defining this class is simply to create a putFormatter() method
     * that is chainable (returns <code>this</code>), so that the
     * HashMap can be populated with static initialization below.
     */
    @SuppressWarnings("serial")
        private static class SimpleDateFormatHashMap extends HashMap<Integer,SimpleDateFormat> {
        public SimpleDateFormatHashMap putFormatter(Integer n, SimpleDateFormat sdf) {
            if (sdf != null) {
                sdf.setTimeZone(mUTCTimeZone);
            }
            this.put(n,sdf);
            return this;
        }
    }

    /**
     * This HashMap stores a SimpleDateFormat instance associated with
     * various string lengths. It is populated with a chain of
     * putFormatter() calls here.
     */
    private static SimpleDateFormatHashMap mFormatters = new SimpleDateFormatHashMap()
        .putFormatter( 4, new SimpleDateFormat("yyyy"))
        .putFormatter( 6, new SimpleDateFormat("yyyyMM"))
        .putFormatter( 8, new SimpleDateFormat("yyyyMMdd"))
        .putFormatter(10, new SimpleDateFormat("yyyyMMddHH"))
        .putFormatter(12, new SimpleDateFormat("yyyyMMddHHmm"))
        .putFormatter(14, new SimpleDateFormat("yyyyMMddHHmmss"))
        .putFormatter(16, new SimpleDateFormat("yyyyMMddHHmmss.S"))
        ;

    @Override public DataValue add(DataMeasure measure) {
        //TODO: Not Yet Implemented
        return null;
    }

}
