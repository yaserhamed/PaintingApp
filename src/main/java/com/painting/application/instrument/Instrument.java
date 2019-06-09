package com.painting.application.instrument;

public class Instrument {
    public Type type;

    Instrument(Type type) {
        this.type = type;
    }

    public enum Type {
        BRUSH,
        PENCIL,
        ERASER,
        FILLER,
        POLYGON,
        ELLIPSE,
        RECTANGLE,
        LINE,
        AIR_BRUSH,
        FILLED_RECTANGLE,
        FILLED_ELLIPSE,
        FILLED_POLYGON,
        PLOT
    }
}
