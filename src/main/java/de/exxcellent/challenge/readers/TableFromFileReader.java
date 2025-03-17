package de.exxcellent.challenge.readers;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Interface to read data tables, which can be stored in two-dimensional arrays, from different file types. Entries and
 * their labels or headers are read separately.
 */
public interface TableFromFileReader {

    /**
     * Method to extract the column names of a data table, stored in a file.
     *
     * @param filepath is the filepath of the file, which should be read.
     * @return {@link ArrayList<String>} of the column headers parsed as Strings.
     * @throws FileNotFoundException in case the file is not found.
     */
    public ArrayList<String> readColumnNames(String filepath) throws FileNotFoundException;

    /**
     * Method to extract the entries of a data table, which is stored in a file, except for the header line.
     *
     * @param filepath filepath of the file, to be read.
     * @return two-dimensional array of Strings, containing the entries of a data table.
     * @throws FileNotFoundException in case the file is not found.
     */
    public String[][] readCellEntries(String filepath) throws FileNotFoundException;
}
