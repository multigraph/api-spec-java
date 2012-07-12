package org.multigraph;

/**
 * A DataVariable object stores the metadata associated with a particular variable
 * in a Data object.  This includes the variable's id, its column number, and its DataValue.Type.
 * <p>
 * The DataVariable object also stores information about whether and how
 * certain special values for the variable should be treated as
 * "missing".  For example, a common technique used in scientific data
 * sets is to use some extreme value, such as -9000, to indicate a
 * placeholder where there should be a value but for which no value is
 * available.  The DataVariable object can be configured to make it very
 * easy to test for values that match a criteria such as this.  This is
 * done through two properties stored by the DataVariable object: a
 * "missing value" (DataValue), and a "missing operator", which
 * should be one of the strings
 * "lt", "le", "eq", "ge", or "gt".  A value for the associated variable
 * is then considered "missing" if it has the relation indicated by 
 * the "missing operator" to the "missing value".
 * <p>
 * The method isMissing(x) will return true if and only if the passed in value (x)
 * has the missing operator's relation to the given value.
 * <p>
 * For example:
 * <pre>
 *    DataVariable var = new DataVariable('x', 0, DataValue.Type.NUMBER, new NumberValue(-9000), "le");
 *    var.isMissing(new NumberValue(100));   // ==> false
 *    var.isMissing(new NumberValue(0));     // ==> false
 *    var.isMissing(new NumberValue(-9000)); // ==> true
 *    var.isMissing(new NumberValue(-9100)); // ==> true
 * </pre>
 * <p>
 * Note that this consideration as "missing" has no significance as far as the actual DataValue
 * object is concerned.  The DataVariable object simply creates a convenient way to set up a test
 * that code using DataValues can use to check whether a particular DataValue matches a criteria
 * intended to indicate a missing value.
 */
public class DataVariable {

    /**
     * The id of this DataVariable.
     */
    private String mId;

    /**
     * Return the id of this DataVariable.
     */
    public String getId() { return mId; }
                
    /**
     * The column number of this DataVariable.
     */
    private int mColumn;

    /**
     * Return the column number of this DataVariable.
     */
    public int getColumn() { return mColumn; }
                
    /**
     * The DataValue.Type of this DataVariable.
     */
    private DataValue.Type mType;

    /**
     * Return the DataValue.Type of this DataVariable.
     */
    public DataValue.Type getType() { return mType; }

    /**
     * Private enum used to represent the missing operator.
     */
    private static enum MissingOperatorCode { NONE, LT, LE, EQ, GE, GT };

    /**
     * The missing operator for this DataVariable.
     */
    private MissingOperatorCode mMissingOperatorCode = MissingOperatorCode.NONE;

    /**
     * The missing value for this DataVariable.
     */
    private DataValue mMissingValue;

    /**
     * Creata new DataVariable with the given id, column number, and DataValue.Type, and with
     * no missing value or missing operator.
     */
    public DataVariable(String id, int column, DataValue.Type type) {
        this(id, column, type, null, null);
    }

    /**
     * Creata new DataVariable with the given id, column number, DataValue.Type, missing
     * value, and missing operator.
     * <p>
     * missingOperator should be one of the strings "lt", "le", "eq", "ge", or "gt".
     */
    public DataVariable(String id, int column, DataValue.Type type, DataValue missingValue, String missingOperator)
    {
        this.mId           = id;
        this.mColumn       = column;
        this.mType         = type;
        this.mMissingValue = missingValue;
        this.mMissingOperatorCode    = parseMissingOperatorCode(missingOperator);
    }
        
    /**
     * Test whether a particular value has the "missing operator" relation to
     * the "missing value".  If this DataVariable has no missing operator, or no
     * missing value, always return false.
     */
    public boolean isMissing(DataValue x) {
        switch (mMissingOperatorCode) {
        case LT:
            return x.compareTo(mMissingValue) < 0;
        case LE:
            return x.compareTo(mMissingValue) <= 0;
        case EQ:
            return x.compareTo(mMissingValue) == 0;
        case GE:
            return x.compareTo(mMissingValue) >= 0;
        case GT:
            return x.compareTo(mMissingValue) > 0;
        }
        return false;
    }

    /**
     * Internal utility function to convert a missing operator string to a constant
     * in the MissingOperatorCode enum.
     */
    private MissingOperatorCode parseMissingOperatorCode(String missingOperator) {
        if (missingOperator==null) { return MissingOperatorCode.NONE; }
        missingOperator = missingOperator.toLowerCase();
        if (missingOperator.equals("lt")) {
            return MissingOperatorCode.LT;
        }
        else if (missingOperator.equals("le")) {
            return MissingOperatorCode.LE;
        }
        else if (missingOperator.equals("eq")) {
            return MissingOperatorCode.EQ;
        }
        else if (missingOperator.equals("ge")) {
            return MissingOperatorCode.GE;
        }
        else if (missingOperator.equals("gt")) {
            return MissingOperatorCode.GT;
        }
        return MissingOperatorCode.NONE;
    }   
        
        
}

