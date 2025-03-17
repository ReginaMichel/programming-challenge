package de.exxcellent.challenge;

import de.exxcellent.challenge.analysers.TableAnalyser;
import de.exxcellent.challenge.readers.TableFromCSVReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Example JUnit 5 test case.
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
class AppTest {

    // While testing the terminal output is caught by an output stream captor, to be able to check whether the texts are
    // as expected.
    private final PrintStream standardOutput = System.out;
    private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();

    /**
     * To check, whether terminal outputs are printed as expected, a new output stream is set up before each unit test.
     */
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputCaptor));
    }

    /**
     * After each test, the standard output stream is restored.
     */
    @AfterEach
    public void tearDown() {
        System.setOut(standardOutput);
    }

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
     * Tests, if default-mode of the main-method is run and if the output to the terminal is like expected.
     *
     * @throws FileNotFoundException in case the file is not found.
     */
    @Test
    void runDefault() throws FileNotFoundException {
        App.main();
        assertEquals("Day with smallest temperature spread : 14", outputCaptor.toString().trim());
    }

    /**
     * Tests, if football-mode of the main-method is run and if the output of the terminal is like expected.
     *
     * @throws FileNotFoundException in case the file is not found.
     */
    @Test
    void runFootball() throws FileNotFoundException {
        App.main("--football", "football.csv");
        assertEquals("Team with smallest goal spread       : Aston_Villa", outputCaptor.toString().trim());
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

    /**
     * Tests the case, that there is more than one row entry, which has the smallest spread regarding the values of two
     * columns. The method findMinimalSpreadViaColumnNames should return the names or IDs of all rows, which satisfy the
     * condition of smallest spread. They should be ordered by ascending row numbers.
     *
     * @throws FileNotFoundException in case the file is not found.
     */
    @Test
    void testMoreThanOneWithMinimalSpread() throws FileNotFoundException {
        String teamsWithMinimalSpread = "Sunderland, Ipswich";
        TableFromCSVReader csvReader = new TableFromCSVReader();
        TableAnalyser tableAnalyser = new TableAnalyser(
                csvReader.readColumnNames("src/main/resources/de/exxcellent/challenge/football.csv"),
                csvReader.readCellEntries("src/main/resources/de/exxcellent/challenge/football.csv"));
        assertEquals(teamsWithMinimalSpread, tableAnalyser.findMinimalSpreadViaColumnName("Team","Wins","Losses"));
    }

    /**
     * Tests the case, if there is a column name misspelled. There should be a {@link NoSuchElementException} stating
     * the name of the column, that is missing or misspelled.
     */
    @Test
    void testColumnNameMisspelled() {
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> App.main("--football", "football_misspelled.csv", "src/test/resources/de/exxcellent/challenge/"));
        assertEquals("File contains no column with name Team.", exception.getMessage());
    }

    /**
     * Tests the case, if there is a file without column names. There should be a {@link NoSuchElementException} stating
     * the name of the column, that is missing or misspelled.
     */
    @Test
    void testFileWithoutHeader() {
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> App.main("--weather", "weather_withoutheader.csv", "src/test/resources/de/exxcellent/challenge/"));
        assertEquals("File contains no column with name Day.", exception.getMessage());
    }

    /**
     * Tests the case, if there is a .csv-file that contains column names, but no data entries. It should be possible to
     * create an object of {@link TableAnalyser} of this file, but analysing the data set should result in a
     * {@link NoSuchElementException} exception with message "Data table contains no entries.".
     */
    @Test
    void testNoEntries() {
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> App.main("--weather", "weather_noentries.csv", "src/test/resources/de/exxcellent/challenge/"));
        assertEquals("Data table contains no entries.", exception.getMessage());
    }

    /**
     * Tests the case, if there are some entries missing in the data set, that are not relevant for the analysis of the
     * smallest temperature spread or the smallest spread of football goals. The analysis should run as usual.
     *
     * @throws FileNotFoundException in case the file is not found.
     */
    @Test
    void testSomeEntriesMissing() throws FileNotFoundException {
        App.main("--weather", "weather_someentriesmissing.csv", "src/test/resources/de/exxcellent/challenge/");
        assertEquals("Day with smallest temperature spread : 14", outputCaptor.toString().trim());
    }

    /**
     * Tests the case, if there are entries missing, which contain data relevant for the analysis. A message should be
     * written in terminal to inform the user, but beside that the analysis should run on the remaining data.
     *
     * @throws FileNotFoundException in case the file is not found.
     */
    @Test
    void testMissingRelevantEntries() throws FileNotFoundException {
        App.main("--weather", "weather_missingtemperatures.csv", "src/test/resources/de/exxcellent/challenge/");
        assertEquals(
                "Some lines of the data set are incomplete. Relevant data is missing. Calculated the result based on remaining data.\r\n" +
                        "Day with smallest temperature spread : 14", outputCaptor.toString().trim());
    }

    /**
     * Tests the case, if entries that are relevant for the analysis contain wrong data formats. A message should be
     * written in terminal to inform the user, but beside that the analysis should run on the remaining data.
     *
     * @throws FileNotFoundException in case the file is not found.
     */
    @Test
    void testTextInsteadOfNumbers() throws FileNotFoundException {
        App.main("--weather", "weather_textinsteadnumbers.csv", "src/test/resources/de/exxcellent/challenge/");
        assertEquals(
                "Some entries contain wrong data format. Ignored those entries and calculated result based on remaining data.\r\n" +
                        "Day with smallest temperature spread : 15", outputCaptor.toString().trim());
    }

    /**
     * Test for the case, that a file with .csv-extention is not in csv format. The result is the same as for a file
     * that is empty. An {@link NoSuchElementException} with message "File is completely empty or has wrong format." is
     * thrown.
     */
    @Test
    void testWrongFileFormat() {
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> App.main("--weather", "weather_notcsvfile.csv", "src/test/resources/de/exxcellent/challenge/"));
        assertEquals("File is completely empty or has wrong format.", exception.getMessage());
    }
}