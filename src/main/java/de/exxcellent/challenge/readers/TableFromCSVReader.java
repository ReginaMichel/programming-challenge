package de.exxcellent.challenge.readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
        // Used ArrayList instead of Arrays, to be flexibel for different amounts of columns.
        ArrayList<String> columnNames = new ArrayList<String>();
        File file = new File(filepath);
        // In case there exists no file with this path, an exception is thrown.
        if (!file.exists()) {
            throw new FileNotFoundException("There is no file with this path.");
        }
        // The file is read with a Scanner and the first line is stored as a String.
        try (Scanner wholeInput = new Scanner(file)) {
            String firstLine = wholeInput.nextLine();
            // Another Scanner separates this String with comma as delimiter to match the .csv format.
            Scanner inputFirstLine = new Scanner(firstLine);
            inputFirstLine.useDelimiter(",");
            while (inputFirstLine.hasNext()) {
                // Each entry of the first line is added to the list of column names.
                columnNames.add(inputFirstLine.next());
            }
        // In case there is no first line or the data can not be read, an Exception is thrown.
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("File is completely empty or has wrong format.");
        }
        return columnNames;
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
