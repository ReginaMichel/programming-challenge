package de.exxcellent.challenge.readers;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Class to read data tables from files in .csv format. Implements Interface {@link TableFromFileReader}.
 */
public class TableFromCSVReader implements TableFromFileReader{
    /**
     * Method to extract the column names of a data table, stored in a file.
     *
     * @param filepath is the filepath of the file, which should be read.
     * @return {@link ArrayList <String>} of the column headers parsed as Strings.
     * @throws FileNotFoundException in case the file is not found.
     */
    public ArrayList<String> readColumnNames(String filepath) throws FileNotFoundException {
        return null;
    }

    /**
     * Method to extract the rows of a data table stored in a file, except for the header line.
     *
     * @param filepath is the filepath of the file, which should be read.
     * @return {@link ArrayList<String>} of lines, which are stored in the file, except for the header.
     * @throws FileNotFoundException in case the file is not found.
     */
    public ArrayList<String> readEntryLines(String filepath) throws FileNotFoundException {
        return null;
    }

    /**
     * Method to extract the entries of a data table, which is stored in a file, except for the header line.
     *
     * @param filepath filepath of the file, to be read.
     * @return two-dimensional array of Strings, containing the entries of a data table.
     * @throws FileNotFoundException in case the file is not found.
     */
    public String[][] readCellEntries(String filepath) throws FileNotFoundException {
        return new String[0][];
    }
}
