package org.multigraph;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * The DatetimeFormatter converts DatetimeValues to strings, according to
 * a format pattern.
 */
public class DatetimeFormatter extends DataFormatter {

    /**
     * An internal reference to the UTC time zone object.
     */
    private static TimeZone mUTCTimeZone = TimeZone.getTimeZone("GMT");

    /**
     * A private internal class used by this Formatter to do the conversion associated
     * with a single '%' conversion character.
     */
    private static class SingleConversionFormatter {
        /**
         * The SimpleDateFormat instance used to do the conversion.
         */
        private SimpleDateFormat mSimpleDateFormat;
        /**
         * The maximum string length returned by this single
         * conversion; this is used to estimate the length of
         * formatted strings.
         */
        private int mMaxLength;
        /**
         * Return the max string length.
         */
        public int getMaxLength() { return mMaxLength; }
        public SingleConversionFormatter(int maxLength, SimpleDateFormat simpleDateFormat) {
            mSimpleDateFormat  = simpleDateFormat;
            mMaxLength         = maxLength;
            if (mSimpleDateFormat != null) {
                mSimpleDateFormat.setTimeZone(mUTCTimeZone);
            }
        }
        /**
         * Return the string that results from this single conversion.  This method gets
         * overridden in anonymous subclasses below in situations where the conversion
         * involves something more complex than a SimpleDateFormat conversion.
         */
        public String format(Date date) {
            if (mSimpleDateFormat != null) {
                return mSimpleDateFormat.format(date);
            }
            return "";
        }
    }

    /**
     * A private internal class used by this Formatter to store a
     * static collection of SingleConversionFormatters, one for each
     * possible '%' conversion character.  The purpose of defining
     * this class is simply to create a putFormatter() method that is
     * chainable (returns <code>this</code>), so that the HashMap can
     * be populated with static initialization below.
     */
    private static class CharFormatterHashMap extends HashMap<Character,SingleConversionFormatter> {
        public CharFormatterHashMap putFormatter(Character c, SingleConversionFormatter scf) {
            this.put(c,scf);
            return this;
        }
    }

    /**
     * This HashMap stores the SingleConversionFormatter associated
     * with each possible '%' conversion character.  It is populated
     * with a chain of putFormatter() calls here.  This is where we
     * see the benefit of all the definitions above --- it lets us
     * define everything associated each '%' conversion character (the
     * '%' character itself, the max converted length, and the
     * SimpleDateFormatter that does the conversion), in just this one
     * place in this file.
     */
    private static CharFormatterHashMap charFormatters = new CharFormatterHashMap()
        // Day without leading zeros:
        .putFormatter('d', new SingleConversionFormatter(2, new SimpleDateFormat("d")))
        // Day with leading zeros:
        .putFormatter('D', new SingleConversionFormatter(2, new SimpleDateFormat("dd")))
        // Month (numerical) without leading zeros:
        .putFormatter('m', new SingleConversionFormatter(2, new SimpleDateFormat("M")))
        // Month (numerical) with leading zeros:
        .putFormatter('M', new SingleConversionFormatter(2, new SimpleDateFormat("MM")))
        // Four digit year:
        .putFormatter('Y', new SingleConversionFormatter(4, new SimpleDateFormat("yyyy")))
        // Two digit year:
        .putFormatter('y', new SingleConversionFormatter(2, new SimpleDateFormat("yy")))
        // Weekday name (spelled out completely, like "Monday", etc):
        .putFormatter('W', new SingleConversionFormatter(9, new SimpleDateFormat("EEEE")))
        // Weekday name, 3-letter abbrev:
        .putFormatter('w', new SingleConversionFormatter(3, new SimpleDateFormat("EEE")))
        // Month name (spelled out completely, like "January", etc):
        .putFormatter('N', new SingleConversionFormatter(9, new SimpleDateFormat("MMMM")))
        // Month name -- 3 letter abbreviation:
        .putFormatter('n', new SingleConversionFormatter(3, new SimpleDateFormat("MMM")))
        // 24 hours:
        .putFormatter('H', new SingleConversionFormatter(2, new SimpleDateFormat("HH")))
        // 12 hours:
        .putFormatter('h', new SingleConversionFormatter(2, new SimpleDateFormat("h")))
        // Minutes:
        .putFormatter('i', new SingleConversionFormatter(2, new SimpleDateFormat("mm")))
        // Seconds:
        .putFormatter('s', new SingleConversionFormatter(2, new SimpleDateFormat("ss")))
        // deciseconds -- 10ths of second -- 1 char:
        .putFormatter('v', new SingleConversionFormatter(1, new SimpleDateFormat("SSS")) {
                public String format(Date date) {
                    return Integer.toString(Integer.parseInt(super.format(date))/100);
                }
            })
        // centiseconds -- 100ths of second -- 2 chars:
        .putFormatter('V', new SingleConversionFormatter(2, new SimpleDateFormat("SSS")) {
                public String format(Date date) {
                    return Integer.toString(Integer.parseInt(super.format(date))/10);
                }
            })
        // milliseconds -- 3 chars:
        .putFormatter('q', new SingleConversionFormatter(3, new SimpleDateFormat("SSS")))
        // AM or PM:
        .putFormatter('P', new SingleConversionFormatter(2, new SimpleDateFormat("a")) {
                public String format(Date date) {
                    return super.format(date).toUpperCase();
                }
            })
        // am or pm:
        .putFormatter('p', new SingleConversionFormatter(2, new SimpleDateFormat("a")) {
                public String format(Date date) {
                    return super.format(date).toLowerCase();
                }
            })
        // newline:
        .putFormatter('L', new SingleConversionFormatter(0, null) {
                public String format(Date date) {
                    return "\n";
                }
            })
        // '%':
        .putFormatter('%', new SingleConversionFormatter(1, null) {
                public String format(Date date) {
                    return "%";
                }
            })
        ;

    /**
     * Create a new DatetimeFormatter with a given format string.  The format string can contain
     * any of the following conversion codes:
     * <p>
     * <table border="1">
     * <tr><td><b>Code</b></td><td><b>Converts To</b></td></tr>
     * <tr><td>%d</td><td>Day without leading zeros</td></tr>
     * <tr><td>%D</td><td>Day with leading zeros (2 digits)</td></tr>
     * <tr><td>%m</td><td>Month (numerical) without leading zeros</td></tr>
     * <tr><td>%M</td><td>Month (numerical, 2 digits) with leading zeros</td></tr>
     * <tr><td>%Y</td><td>Four digit year</td></tr>
     * <tr><td>%y</td><td>Two digit year</td></tr>
     * <tr><td>%W</td><td>Weekday name (spelled out completely, like "Monday", etc)</td></tr>
     * <tr><td>%w</td><td>Weekday name, 3-letter abbreviation</td></tr>
     * <tr><td>%N</td><td>Month name (spelled out completely, like "January", etc)</td></tr>
     * <tr><td>%n</td><td>Month name -- 3 letter abbreviation</td></tr>
     * <tr><td>%H</td><td>hours, 00-24 hours, 2 digits</td></tr>
     * <tr><td>%h</td><td>hours, 0-12 hours</td></tr>
     * <tr><td>%i</td><td>Minutes (2 digits)</td></tr>
     * <tr><td>%s</td><td>Seconds (2 digits)</td></tr>
     * <tr><td>%v</td><td>deciseconds -- 10ths of second -- 1 char</td></tr>
     * <tr><td>%V</td><td>centiseconds -- 100ths of second -- 2 chars</td></tr>
     * <tr><td>%q</td><td>milliseconds -- 3 chars</td></tr>
     * <tr><td>%P</td><td>AM or PM</td></tr>
     * <tr><td>%p</td><td>am or pm</td></tr>
     * <tr><td>%L</td><td>newline</td></tr>
     * <tr><td>%%</td><td>a single % character</td></tr>
     * </table>
     *
     * @param format The format string for this formatter.
     */
    public DatetimeFormatter(String format) {
        super(format);
    }


    /**
     * Convert a given value to a string according to the conversion determined by this DatetimeFormatter's format string.
     *
     * @param  value The value to be converted
     * @return The formatted String
     */
    @Override public String format(DataValue value) {
        Date date = ((DatetimeValue)value).getDateValue();
        StringBuffer sb = new StringBuffer();
        int index = 0;
        while (index < mFormat.length()) {
            if (mFormat.charAt(index) == '%') {
                ++index;
                sb.append(formatSingleChar(mFormat.charAt(index), date));
            } else {
                sb.append(mFormat.charAt(index));
            }
            ++index;
        }
        return sb.toString();
    }

    /**
     * Return the string that results from applying a single '%' character
     * conversion to a date.
     *
     * @param c The '%' code
     * @param date The date value
     * @return The formatted string
     */
    private static String formatSingleChar(char c, Date date) {
        SingleConversionFormatter scf = charFormatters.get(c);
        if (scf != null) {
            return scf.format(date);
        }
        return new String(new char[] { c });
    }

    @Override public int getMaxLength() {
        int length = 0;
        int index = 0;
        while (index < mFormat.length()) {
            if (mFormat.charAt(index) == '%') {
                ++index;
                length += maxLengthSingleChar(mFormat.charAt(index));
            } else {
                length += 1;
            }
            ++index;
        }
        return length;
    }

    /**
     * Return the maximum length of a string generated by a single '%' conversion
     * character.
     */
    private static int maxLengthSingleChar(char c) {
        SingleConversionFormatter scf = charFormatters.get(c);
        if (scf != null) {
            return scf.getMaxLength();
        }
        return 1;
    }



}
