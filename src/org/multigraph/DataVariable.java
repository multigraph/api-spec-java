package org.multigraph;

import org.multigraph.datatypes.DataType;
import org.multigraph.datatypes.DataValue;

public class DataVariable {

    private String mId;
    public String getId() { return mId; }
                
    private int mColumn;
    public int getColumn() { return mColumn; }
                
    private DataType mType;
    public DataType getType() { return mType; }

    private static enum MissingOp { NONE, LT, LE, EQ, GE, GT };
    private MissingOp mMissingOp = MissingOp.NONE;
    private DataValue mMissingValue;

    public DataVariable(String id, int column, DataType type, DataValue missingValue) {
        this(id, column, type, missingValue, null);
    }
    public DataVariable(String id, int column, DataType type) {
        this(id, column, type, null, null);
    }

    public DataVariable(String id, int column, DataType type, DataValue missingValue, String missingOpString)
    {
        this.mId           = id;
        this.mColumn       = column;
        this.mType         = type;
        this.mMissingValue = missingValue;
        this.mMissingOp    = parseMissingOpString(missingOpString);
    }
        
    public boolean isMissing(DataValue x) {
        switch (mMissingOp) {
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

    private MissingOp parseMissingOpString(String missingOpString) {
        if (missingOpString==null) { return MissingOp.NONE; }
        missingOpString = missingOpString.toLowerCase();
        if (missingOpString.equals("lt")) {
            return MissingOp.LT;
        }
        else if (missingOpString.equals("le")) {
            return MissingOp.LE;
        }
        else if (missingOpString.equals("eq")) {
            return MissingOp.EQ;
        }
        else if (missingOpString.equals("ge")) {
            return MissingOp.GE;
        }
        else if (missingOpString.equals("gt")) {
            return MissingOp.GT;
        }
        return MissingOp.NONE;
    }   
        
        
}

