package de.exxcellent.challenge.analysers;

import java.util.ArrayList;
import java.util.NoSuchElementException;

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

    /**
     * Method to find the row(s), where the difference of the values of two specific columns is minimal, based on the
     * names of the columns.
     *
     * @param rowID the name of the column, which stores the ID or name of a row entry.
     * @param firstValue the name of the column, which stores one of the values, that should be compared.
     * @param secondValue the name of the column, which stores the other value, that should be compared.
     * @return a String, which contains the name(s) or ID(s) of the rows, where the difference is minimal.
     */
    public String findMinimalSpreadViaColumnName(String rowID, String firstValue, String secondValue) {
        // If there are no columns with the specified names in columnNames, Exceptions are thrown.
        if (!columnNames.contains(rowID)) {
            throw new NoSuchElementException("File contains no column with name " + rowID + ".");
        } else if (!columnNames.contains(firstValue)) {
            throw new NoSuchElementException("File contains no column with name " + firstValue + ".");
        } else if (!columnNames.contains(secondValue)) {
            throw new NoSuchElementException("File contains no column with name " + secondValue + ".");
        }
        // Else the indices of the columns are determined and the row(s) of minimal spread are calculated based on the
        // column number:
        return findMinimalSpreadViaColumnNumber(
                columnNames.indexOf(rowID), columnNames.indexOf(firstValue), columnNames.indexOf(secondValue));
    }

    /**
     * Method to find the row(s), where the difference of the values of two specific columns is minimal, based on the
     * numbers of the columns.
     *
     * @param columnRowID number of the column, which stores the ID or name of a row entry.
     * @param columnFirstValue number of the column, which stores one of the values, that should be compared.
     * @param columnSecondValue number of the column, which stores the other value, that should be compared.
     * @return a String, which contains the name(s) or ID(s) of the rows, where the difference is minimal.
     */
    public String findMinimalSpreadViaColumnNumber(int columnRowID, int columnFirstValue, int columnSecondValue) {
        // If there are no data entries, an Exception is thrown.
        if (tableEntries.length == 0) {
            throw new NoSuchElementException("Data table contains no entries.");
        }
        // Start-values:
        int rowOfMinimalSpread = 0;
        float minimalDifference = Float.MAX_VALUE;
        // In case some of the rows are incomplete or contain entries of wrong types, the method will catch thrown
        // Exceptions and try to proceed with the remaining entries. If Exceptions were thrown will be stored with
        // booleans.
        boolean dataSetIsIncomplete = false;
        boolean dataContainsWrongFormat = false;
        // A for-loop loops through all entries of the data table and calculates the difference of the values of the two
        // columns. If the spread of the values is smaller than the previous minimal spread, the new smaller difference
        // is stored and the number of the row, where this new minimum, was reached.
        for (int i = 0; i < tableEntries.length; i++) {
            try {
                float newDiff = Math.abs(Float.parseFloat(tableEntries[i][columnSecondValue])
                        - Float.parseFloat(tableEntries[i][columnFirstValue]));
                if (newDiff < minimalDifference) {
                    minimalDifference = newDiff;
                    rowOfMinimalSpread = i;
                }
            // In case a value was missing or of wrong type, the Exceptions are caught and the procedure proceeds with
            // the remaining values:
            } catch (NullPointerException e) {
                dataSetIsIncomplete = true;
            } catch (NumberFormatException e) {
                dataContainsWrongFormat = true;
            }
        }
        // In case values were missing in every row, and Exception is thrown:
        if (minimalDifference == Float.MAX_VALUE) {
            throw new RuntimeException("Minimal spread could not be calculated, because all lines where missing at " +
                    "least one of the two values, which should be compared.");
        }
        // The ID or name of the row-entry, where the minimal spread was reached is stored in a String.
        String rowIDOfMinimalSpread = tableEntries[rowOfMinimalSpread][columnRowID];
        // If more than one row has the smallest spread, the IDs or names should be added to the String.
        for (int i = rowOfMinimalSpread; i < tableEntries.length; i++) {
            try {
                if (i != rowOfMinimalSpread &&
                        minimalDifference == Math.abs(Float.parseFloat(tableEntries[i][columnSecondValue])
                                - Float.parseFloat(tableEntries[i][columnFirstValue]))) {
                    rowIDOfMinimalSpread = rowIDOfMinimalSpread + ", " + tableEntries[i][columnRowID];
                }
            // The Exceptions can be ignored, because they should be known already from the first loop.
            } catch (NullPointerException | NumberFormatException ignored) {
            }
        }
        // If rows were incomplete or contained entries with wrong format, the user is informed via messages in the
        // terminal.
        if (dataSetIsIncomplete) {
            System.out.println("Some lines of the data set are incomplete. Relevant data is missing. " +
                    "Calculated the result based on remaining data.");
        }
        if (dataContainsWrongFormat) {
            System.out.println("Some entries contain wrong data format. Ignored those entries and calculated result " +
                    "based on remaining data.");
        }
        // Finally the name(s) or ID(s) of the row entry/ies are returned.
        return rowIDOfMinimalSpread;
    }
}