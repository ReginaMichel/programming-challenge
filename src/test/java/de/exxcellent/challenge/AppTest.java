package de.exxcellent.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Example JUnit 5 test case.
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
class AppTest {

    private String successLabel = "not successful";

    @BeforeEach
    void setUp() {
        successLabel = "successful";
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
     * Tests, if default-mode of the main-method is run.
     */
    @Test
    void runDefault() {
        App.main();
    }

    /**
     * Tests, if football-mode of the main-method is run.
     */
    @Test
    void runFootball() {
        App.main("--football", "football.csv");
    }
}