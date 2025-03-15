package de.exxcellent.challenge.analysers;

import java.util.ArrayList;

/**
 * Class to analyse data of data tables, which can be provided by
 * {@link de.exxcellent.challenge.readers.TableFromFileReader}.
 */
public class TableAnalyser {

    // Column names and entries of a data table. Stored as Strings to have full flexibility regarding primitive data
    // types, Strings and Dates.
    private ArrayList<String> columnNames;
    private String[][] tableEntries;

    /**
     * Constructor.
     *
     * @param columnNames an {@link ArrayList<String>}, which contains column names.
     * @param tableEntries a two-dimensional array of Strings, which contains the cell entries of the table.
     */
    public TableAnalyser(ArrayList<String> columnNames, String[][] tableEntries) {
        this.columnNames = columnNames;
        this.tableEntries = tableEntries;
    }
}