package com.painting.application.instrument;

import static com.painting.application.instrument.Instrument.Type.*;

public class InstrumentFactory {

    public static Instrument anInstrument(Instrument.Type type) {
        if (type == BRUSH) {
            return new Instrument(BRUSH);
        } else if (type == PENCIL) {
            return new Instrument(PENCIL);
        } else if (type == ERASER) {
            return new Instrument(ERASER);
        } else if (type == FILLER) {
            return new Instrument(FILLER);
        } else if (type == POLYGON) {
            return new Instrument(POLYGON);
        } else if (type == ELLIPSE) {
            return new Instrument(ELLIPSE);
        } else if (type == RECTANGLE) {
            return new Instrument(RECTANGLE);
        } else if (type == LINE) {
            return new Instrument(LINE);
        } else if (type == AIR_BRUSH) {
            return new Instrument(AIR_BRUSH);
        } else if (type == FILLED_RECTANGLE) {
            return new Instrument(FILLED_RECTANGLE);
        } else if (type == FILLED_ELLIPSE) {
            return new Instrument(FILLED_ELLIPSE);
        } else if (type == FILLED_POLYGON) {
            return new Instrument(FILLED_POLYGON);
        } else if (type == PLOT) {
            return new Instrument(PLOT);
        }

        throw new IllegalArgumentException("No instrument of type: " + type.name());
    }

}

