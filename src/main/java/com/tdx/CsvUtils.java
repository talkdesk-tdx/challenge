package com.tdx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

class CsvUtils {
    /**
     * Parses events from a CSV file. It's assumed that the file has a header column that is ignored by the parsing.
     *
     * @param fileResourcePath the path in the resource folder for the file containing the data.
     * @param separator        the separator of the data
     * @return a list of the columns parsed as doubles wrapped in a list
     */
    static List<Event> parseEventsFromCsvFile(final String fileResourcePath, final String separator) {
        List<List<String>> records = new ArrayList<>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(getFileFromResources(fileResourcePath)))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(separator);
                    records.add(Arrays.asList(values));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records.stream()
                .skip(1)//skip header
                .map(CsvUtils::getEventFromRecord)
                .collect(toList());
    }

    private static Event getEventFromRecord(final List<String> record) {
        return new Event(
                Instant.parse(record.get(0)),
                Instant.parse(record.get(1)),
                record.get(2).equals("true"),
                Integer.valueOf(record.get(3)),
                Long.valueOf(record.get(4)));
    }

    private static File getFileFromResources(String fileName) {

        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        final URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }
}
