package org.multigraph;

/**
 * The NumberFormatter converts NumberValues to strings, according to
 * a format pattern.
 */
public class NumberFormatter extends DataFormatter {

    /**
     * Create a new NumberFormatter with a given format string.  The format string can contain
     * just about any printf-style % conversion character.  (Details to be better documented here
     * later.)
     *
     * @param format The format string for this formatter.
     */
    public NumberFormatter(String format) {
        super(format);
    }

    /**
     * Convert a given value to a string according to the conversion determined by this NumberFormatter's format string.
     *
     * @param  value The value to be converted
     * @return The formatted String
     */
    @Override public String format(DataValue value) {
        try {
            return String.format(mFormat, value.getRealValue());
        } catch (java.util.IllegalFormatConversionException e) {
            // If the above formatting fails due to IllegalFormatConversionException, it
            // might be because the format string uses a 'd' format code, which Java
            // does not allow for double values.  Multigraph DOES allow it, though, with
            // the assumption being that the double value should be rounded to the nearest
            // integer.  So try that conversion here.
            return String.format(mFormat, Math.round(value.getRealValue()));
        }
    }

    /**
     * Return the maximum string length that this formatter typically generates.
     */
    @Override public int getMaxLength() {
        // TODO: Not Yet Implemented
        return 0;
    }
}
