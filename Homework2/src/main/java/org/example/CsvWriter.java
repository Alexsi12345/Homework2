package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
    public static void writeToCsv(String fileName, int totalLeavesCount) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Total Leaves Count\n");
            writer.write(totalLeavesCount + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}