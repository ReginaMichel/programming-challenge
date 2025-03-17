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
        // The lines are stored as ArrayList, to be flexible regarding the total amount of lines in a file.
        ArrayList<String> lines = new ArrayList<String>();
        File file = new File(filepath);
        if (!file.exists()) {
            throw new FileNotFoundException("There is no file with this path.");
        }
        // A scanner reads the whole file and adds every line to the list.
        try (Scanner wholeInput = new Scanner(file)) {
            // Skip first line, because it stores the column titles.
            wholeInput.nextLine();
            while(wholeInput.hasNextLine()){
                lines.add(wholeInput.nextLine());
            }
        // In case there is no data or the file cannot be read properly, an exception is thrown and passed on.
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("File is completely empty or has wrong format.");
        }
        // Returns a list of all lines except the column headers.
        return lines;
    }

    /**
     * Method to extract the entries of a data table, which is stored in a file, except for the header line.
     *
     * @param filepath filepath of the file, to be read.
     * @return two-dimensional array of Strings, containing the entries of a data table.
     * @throws FileNotFoundException in case the file is not found.
     */
    public String[][] readCellEntries(String filepath) throws FileNotFoundException {
        // readColumnNames(filepath) is called to obtain the number of columns expected for each row in the table.
        int columnNumber = readColumnNames(filepath).size();
        // readEntryLines(filepath) is called to obtain the content of the data table except the header.
        ArrayList<String> lines = readEntryLines(filepath);
        String[][] entries = new String[lines.size()][columnNumber];
        // In case there are too short or too long lines, the user is notified via console output.
        boolean dataIncomplete = false;
        boolean tooManyEntriesPerLine = false;
        // For each row, a Scanner is created, which separates the entries via commas, that are expected in .csv-files.
        for(int i = 0; i < lines.size(); i++) {
            Scanner inputLine = new Scanner(lines.get(i));
            inputLine.useDelimiter(",");
            int j = 0;
            // The additional condition "hasNext()" is important, because it might be that there are entries missing in
            // some of the lines.
            while(inputLine.hasNext() && j < columnNumber){
                entries[i][j] = inputLine.next();
                j++;
            }
            if (j < columnNumber) {
                dataIncomplete = true;
            }
            if (inputLine.hasNext()) {
                tooManyEntriesPerLine = true;
            }
        }
        // In case there are lines with missing entries, the user is notified via console output.
        if (dataIncomplete) {
            System.out.println("Some lines of the data set are incomplete. Please verify relevant data entries are " +
                    "in the right place.");
        }
        if (tooManyEntriesPerLine) {
            System.out.println("Some lines have too many entries. Please crosscheck your input file.");
        }
        // The content is returned as a two-dimensional String-Array.
        return entries;
    }
}