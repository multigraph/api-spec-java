package org.multigraph;

import java.util.ArrayList;

/**
 * This is the (abstract) superclass for classes that Multigraph
 * uses for loading, storing, and managing data to be plotted.  When
 * drawing a plot, Multigraph accesses the data through the methods of
 * this class.  Individual subclasses implement their own ways of
 * loading and managing data.
 * <p>
 * Regardless of the details of the underlying data retrieval and/or
 * storage implementation, all Data objects share a common abstract model
 * for accessing their data.  The model is based on the concept of
 * sequences of data rows, such as in a spreadsheet or table.  Each row
 * contains a set of DataValues, and within a single Data object, all
 * rows must have exactly the same number of values, or columns.  The
 * first value in the row is referred to as column 0, the next value as
 * column 1, and so on.
 * <p>
 * A Data object also stores metadata about its columns: an
 * id (which is just a String) and a data type (DataValue.Type constant)
 * for each column.
 * <p>
 * <b>Static vs Dynamic Data Objects</b>
 * <p>
 * Broadly speaking, there are two kinds of concrete subclasses of
 * the Data class, depending on how the class obtains its data.
 * A "static" Data class obtains all its data at once, when it is
 * initialized.  All data rows are stored in the object and are immediately
 * available as soon as the object has been initialized.
 * <p>
 * A "dynamic" Data class, on the other hand, deals with data that must
 * be loaded from a (possibly remote) data store.  For a dynamic Data object,
 * there is a distinction between data that is immediately
 * available, and data that exists in the (remote) data store but that has
 * not yet been loaded into the Data object.  Dynamic Data objects typically work
 * asynchronously: a request to access a certain range of data does not necessarily
 * return all of the requested data immediately.  Instead, the object returns whatever
 * data within the the requested range is immediately available, but also initiates
 * a request to fetch any addtional needed rows from the data store.  The Data object
 * also calls its Data.ReadyHandler object's dataReady()
 * once the additional data has been received and is ready.
 */
public abstract class Data {

    /**
     * Objects adhering to the Data.ReadyHandler interface can be registered with
     * a Data object via its onReady() method; the registered object's dataReady()
     * method will be called whenever new data is available in the Data object.
     */
    public interface ReadyHandler {
        /**
         * Method to call when new data is available.
         *
         * @param min minimum value of range of new data
         * @param max maximum value of range of new data
         */
        public void dataReady(DataValue min, DataValue max);
    }

    /**
     * Objects adhering to the Data.Iterator interface 
     * act as iterators for stepping through rows
     * from a Data object.
     */
    public interface Iterator {
        /**
         * Return true if there are more rows left for this iterator to return (i.e. if the next
         * call to next() would return a non-null result).
         */
        public boolean hasNext();
        /**
         * Return an array of the requested DataValues from the next row for this iterator.
         */
        public DataValue[] next();
    }

    /**
     * 'mColumns' is an array of DataVariable instances representing the metadata
     * for this Data object's columns.
     */
    protected ArrayList<DataVariable> mColumns;


    /**
     * Return an array of DataVariable instances representing the metadata
     * for this Data object's columns.
     */
    public ArrayList<DataVariable> getColumns() { return mColumns; }

    /**
     * Create a new Data object with the given array of DataVariable instances.
     * This constructor is protected in order to restrict use to subclasses: you
     * can't create an instance of the Data class directly; only subclasses
     * may do so, by calling super(columns).
     */
    protected Data(ArrayList<DataVariable> columns) {
        mColumns = columns;
    }

    /**
     * Return a Data.Iterator object for iterating over a sequence of rows of data.
     * The rows are defined by the <code>min</code>, <code>max</code>, and <code>buffer</code>
     * parameters.  These parameters define what is known as the "iterator range", which
     * is explained below.
     * <p>
     * The columns that will be returned by the iterator are determined by the <code>columnIds</code>
     * parameter; these are called the "iterator columns".  Calls to the returned iterator's
     * next() method should return an array containing exactly one value for each entry
     * in the <code>columnIds</code> array.
     * <p>
     * <b>Iterator Range</b>
     * <p>
     * The <code>min</code>, <code>max</code>, and <code>buffer</code> parameters determine a
     * range of data rows consisting of rows of data in the Data object for which the column 0
     * value is greater than or equal to <code>min</code>, and less than or
     * equal to <code>max</code>.  Furthermore, if <code>buffer</code> is
     * nonzero, the iterator range includes <code>buffer</code> additional
     * rows outside each end of the range.
     * <p>
     * For example, if this Data object contains the following rows
     * <pre>
     *       6.5,23,42
     *       7.0,92,93
     *       7.5,45,38
     *       8.0,63,87
     *       8.5,54,42
     *       9.0,47,94
     *       9.5,44,65
     * </pre>
     * and getIterator is called with <code>min=7.5</code>,
     * <code>max=8.5</code>, and <code>buffer=1</code>, then the returned
     * iterator should step through the following 5 rows (the ones where
     * column 0 is between 7.5 and 8.5, inclusive, plus one additional one
     * before and after that range):
     * <pre>
     *       7.0,92,93
     *       7.5,45,38
     *       8.0,63,87
     *       8.5,54,42
     *       9.0,47,94
     * </pre>
     * For a static Data object, getIterator() should return an iterator that will
     * exactly step over the rows in the iterator range.  For a dynamic Data object,
     * though, the iterator will only step over those rows in the iterator range
     * that are immediately available in the Data object (i.e. rows that have previously
     * been loaded).  Before returning the iterator, getIterator() should also initiate
     * whatever asynchronous operations are needed to load any additional rows in the
     * iterator range.  Those operations should run asynchronously, and when they
     * are complete, the Data object should call its Data.ReadyHandler object's dataReady()
     * method.
     * <p>
     * <b>NOTE:</b> the rows in the Data object are always assumed to be sorted in
     * increasing order of column 0, and this is always the column that is compared
     * against
     * <code>min</code> and <code>max</code> to determine the iterator range.
     * This is the case even if column 0 is not one of the ones
     * listed in <code>columnIds</code>!  Column 0 is special: it is the (only) column by which
     * the data is ordered.
     *
     * @param columnIds the ids of the columns whose values should be returned by the iterator
     * @param min the minimum DataValue in the desired range
     * @param max the maximum DataValue in the desired range
     * @param buffer number of extra values to include outside each end of the range
     */
    public Data.Iterator getIterator(String columnIds[], DataValue min, DataValue max, int buffer) { return null; }

    /**
     * Register a handler to be called when new data becomes available in this Data object.  Static Data subclasses
     * can ignore this method.  Dynamic ones should override it with an implementation that stores the handler
     * and arranges for it to be called when new data is available.
     */
    public void onReady(Data.ReadyHandler readyHandler) {
        //TODO: Not Yet Implemented
    }

    /**
     * Return the upper and lower limit of a given column in this Data object.  It is not yet at all clear
     * what this function should do for dynamic (such as web service based) Data objects.  Just ignore it for the
     * moment.
     *
     * @return an array of two DataValues: [minimum, maximum]
     */
    public DataValue[] getBounds(String columnId) { return null; }


    /**
     * Return the id of this data object's i-th column  Return null if there
     * is no i-th column.
     */
    public String getColumnId(int i) {
        if (i < 0 || i >= mColumns.size()) {
            return null;
        }
        return mColumns.get(i).getId();
    }


    /**
     * Return the column number (index in the mColumns array) of one
     * of this Data object's columns, given a string containing a
     * column id.  Returns -1 if there is no column with the given
     * id in this data object.
     */
    protected int columnIdToColumnNumber(String id) {
        for (int j=0; j<mColumns.size(); ++j) {
            if (id.equals(mColumns.get(j).getId())) {
                return j;
            }
        }
        return -1;
    }

    /**
     * Return one
     * of this Data object's columns, given a string containing a
     * column id.  Returns null if there is no column with the given
     * id in this data object.
     */
    public DataVariable columnIdToDataVariable(String id) {
        for (int j=0; j<mColumns.size(); ++j) {
            if (id.equals(mColumns.get(j).getId())) {
                return mColumns.get(j);
            }
        }
        return null;
    }
        
}

