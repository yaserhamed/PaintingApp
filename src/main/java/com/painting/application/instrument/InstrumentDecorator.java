package com.painting.application.instrument;

import java.awt.*;

public class InstrumentDecorator  {
    public int fontWidth;
    private Color color;
    private Instrument instrument;

    public InstrumentDecorator(Color color, int fontWidth, Instrument instrument) {
        this.instrument = instrument;
        this.color = color;
        this.fontWidth = fontWidth;
    }

    public Instrument.Type getType() {
        return instrument.type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getFontWidth() {
        return fontWidth;
    }

    public void setFontWidth(int fontWidth) {
        this.fontWidth = fontWidth;
    }
}
