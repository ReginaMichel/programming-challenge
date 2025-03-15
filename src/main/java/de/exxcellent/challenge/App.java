package de.exxcellent.challenge;

import de.exxcellent.challenge.readers.TableFromCSVReader;
import de.exxcellent.challenge.readers.TableFromFileReader;

/**
 * The entry class for the weather-data programming-challenge. Weather data or football results are read from csv-files
 * and analysed. Either the day with smallest temperature spread or the football team with smallest goal spread are
 * calculated.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 * @author Regina Michel <regina_michel@outlook.de>
 */
public final class App {

    /**
     * Main entry method of reading and analysing weather data or football results stored in csv-files.
     *
     * @param args The CLI arguments passed. Possible arguments are
     *             "--weather" or "--football" as first entry,
     *             "weather.csv", "football.csv", or other filenames as second entry,
     *             filepaths as third entry.
     *             If no arguments are passed, the default is the analysis of weather data.
     */
    public static void main(String... args) {

        // The default mode of the application is to analyse weather data of the file
        // src/main/resources/de/exxcellent/challenge/weather.csv, but other modes, data types, or filepaths can be
        // passed via arguments of the main method:
        String mode = args.length > 0 ? args[0] : "--weather";
        String fileName = args.length > 1 ? args[1] : "weather.csv";
        String filePath = args.length > 2 ? args[2] : "src/main/resources/de/exxcellent/challenge/";

        // A class to read the specific file format is chosen depending on the extention of the filename:
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
        TableFromFileReader reader;
        switch (fileExtension) {
            case "csv":
                reader = new TableFromCSVReader();
                break;
            default:
                // Since other file formats are not supported yet, an exception is thrown, in case one tries.
                throw new UnsupportedOperationException("File extension is not supported. Please try .csv-files.");
        }

        // Switch to decide if weather or football data should be analysed:
        switch (mode) {
            case "--weather":
                String dayWithSmallestTempSpread = "Someday";     // Your day analysis function call …
                System.out.printf("Day with smallest temperature spread : %s%n", dayWithSmallestTempSpread);
                break;
            case "--football":
                String teamWithSmallestGoalSpread = "A good team"; // Your goal analysis function call …
                System.out.printf("Team with smallest goal spread       : %s%n", teamWithSmallestGoalSpread);
                break;
            default:
                // Other cases are not supported yet. An exception is thrown.
                throw new UnsupportedOperationException("Analysis mode not supported. Please try \"--weather\" or " +
                        "\"--football\".");
        }
    }
}
