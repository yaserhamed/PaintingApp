package com.painting.application.stroke;

import com.painting.application.Application;

import javax.swing.*;
import java.awt.*;

public class StrokePanel extends JPanel {

    StrokePanel() {
        setPreferredSize(new Dimension(84, 84));
    }

    public void paintComponent(Graphics g) {
        g.setColor(Application.ui.drawingPanel.currentDecoratedInstrument.getColor());
        g.fillRect(getWidth() / 2 - getFontWidth() / 2, getHeight() / 2 - getFontWidth() / 2,
                getFontWidth(), getFontWidth());
        g.setColor(Color.black);
        g.drawRect(getWidth() / 2 - getFontWidth() / 2, getHeight() / 2 - getFontWidth() / 2,
                getFontWidth(), getFontWidth());
    }

    private int getFontWidth() {
        return Application.ui.drawingPanel.currentDecoratedInstrument.getFontWidth();
    }
}