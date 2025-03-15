package de.exxcellent.challenge;

import de.exxcellent.challenge.readers.TableFromCSVReader;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Example JUnit 5 test case.
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
class AppTest {

 /*   private String successLabel = "not successful";

    @BeforeEach
    void setUp() {
        successLabel = "successful";
    } */

    /**
     * Tests, what happens if one passes file extentions to the main method, which are not supported yet. An
     * {@link UnsupportedOperationException} with message "File extension is not supported. Please try .csv-files."
     * should be thrown.
     */
    @Test
    void unsupportedFileFormats() {
        Exception exception = assertThrows(UnsupportedOperationException.class,
                () -> App.main("--football", "football_xlsxfile.xlsx", "src/test/resources/de/exxcellent/challenge/"));
        assertEquals("File extension is not supported. Please try .csv-files.", exception.getMessage());
    }

    /**
     * Tests, what happens if one passes a mode to the main method, which is not supported yet. An
     * {@link UnsupportedOperationException} with message "Analysis mode not supported. Please try \"--weather\" or " +
     *                 "\"--football\"." should be thrown.
     */
    @Test
    void unsupportedModes() {
        Exception exception = assertThrows(UnsupportedOperationException.class,
                () -> App.main("--elections", "weather.csv", "src/main/resources/de/exxcellent/challenge/"));
        assertEquals("Analysis mode not supported. Please try \"--weather\" or " +
                "\"--football\".", exception.getMessage());
    }

    /**
     * Tests, if default-mode of the main-method is run.
     *
     * @throws FileNotFoundException in case the file is not found.
     */
    @Test
    void runDefault() throws FileNotFoundException {
        App.main();
    }

    /**
     * Tests, if football-mode of the main-method is run.
     *
     * @throws FileNotFoundException in case the file is not found.
     */
    @Test
    void runFootball() throws FileNotFoundException {
        App.main("--football", "football.csv");
    }

    /**
     * Tests, if the correct {@link FileNotFoundException} is thrown, in case the file name or file path are wrong.
     */
    @Test
    void testFileNotFound() {
        Exception exception = assertThrows(FileNotFoundException.class,
                () -> new TableFromCSVReader().readColumnNames("misspelled/path/file.csv"));
        assertEquals("There is no file with this path.", exception.getMessage());
    }

    /**
     * Tests, if {@link TableFromCSVReader} read column names correctly.
     *
     * @throws FileNotFoundException in case the file does not exist with this path.
     */
    @Test
    void testReadColumnNames() throws FileNotFoundException {
        ArrayList<String> columnNames = new ArrayList<String>();
        columnNames.add("Day");
        columnNames.add("MxT");
        columnNames.add("MnT");
        columnNames.add("AvT");
        columnNames.add("AvDP");
        columnNames.add("1HrP TPcpn");
        columnNames.add("PDir");
        columnNames.add("AvSp");
        columnNames.add("Dir");
        columnNames.add("MxS");
        columnNames.add("SkyC");
        columnNames.add("MxR");
        columnNames.add("Mn");
        columnNames.add("R AvSLP");
        assertEquals(columnNames, new TableFromCSVReader().readColumnNames("src/main/resources/de/exxcellent/challenge/weather.csv"));
    }

    /**
     * Tests, if {@link TableFromCSVReader} reads rows of .csv files properly.
     *
     * @throws FileNotFoundException in case the file is not found.
     */
    @Test
    void testReadLines() throws FileNotFoundException {
        String testLine = "Ipswich,38,9,9,20,41,64,36";
        assertEquals(testLine, new TableFromCSVReader().readEntryLines("src/main/resources/de/exxcellent/challenge/football.csv").get(17));
    }

    /**
     * Tests, if {@link TableFromCSVReader} reads entries of data tables in .csv files properly.
     *
     * @throws FileNotFoundException in case the file is not found.
     */
    @Test
    void testReadCellEntries() throws FileNotFoundException {
        String testCell = "1012.7";
        assertEquals(testCell, new TableFromCSVReader().readCellEntries("src/main/resources/de/exxcellent/challenge/weather.csv")[5][13]);
    }
}