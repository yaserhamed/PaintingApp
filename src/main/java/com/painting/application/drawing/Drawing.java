package com.painting.application.drawing;

import com.painting.application.instrument.Instrument;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Drawing {
    private static final String FILE_NAME = "TempOutput.vec";

    private Instrument.Type type;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public Drawing(Instrument.Type type, int x1, int y1, int x2, int y2) {
        this.type = type;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void writeToFile() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("TempOutput.vec", true));

        writer.append(type.name().toUpperCase())
                .append(" ")
                .append(String.valueOf(x1))
                .append(" ")
                .append(String.valueOf(y1))
                .append(" ")
                .append(String.valueOf(x2))
                .append(" ")
                .append(String.valueOf(y2));

        writer.newLine();
        writer.close();
    }
}