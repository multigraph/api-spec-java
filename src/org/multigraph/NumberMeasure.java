package org.multigraph;

/**
 * A NumberMeasure stores a measure of length (and direction) along a number axis.  This is simply
 * represented as a floating point number, which is the "real" value of the measure.
 */
public class NumberMeasure extends DataMeasure {

    /**
     * The real value of this measure.
     */
    protected double mValue;

    /**
     * Construct a new NumberMeasure with a given real value.
     */
    public NumberMeasure(double realValue) {
        this.mValue = realValue;
    }

    /**
     * Construct a new NumberMeasure by parsing a string; the string
     * should be parsable as a floating point number.
     */
    public NumberMeasure(String value) {
        this.mValue = Double.parseDouble(value);
    }

    /**
     * Return the real value of this NumberMeasure --- i.e. its length.
     */
    @Override
        public double getRealValue() {
        return mValue;
    }

    @Override
        public String toString() {
        return String.format("%f", mValue);
    }

    @Override public DataValue firstSpacingLocationAtOrAfter(DataValue value, DataValue alignment) {
        //TODO: Not Yet Implemented
        return null;
    }

}
