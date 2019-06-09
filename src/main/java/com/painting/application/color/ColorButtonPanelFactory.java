package com.painting.application.color;

import javax.swing.*;
import java.awt.*;

import static com.painting.application.direction.Direction.*;
import static java.awt.Color.LIGHT_GRAY;

public class ColorButtonPanelFactory {
    public static JPanel create(ColorPanel colorButtonsPanel) {
        JPanel result = new JPanel();

        result.setLayout(new BorderLayout());
        result.add(new ColorPanel(LIGHT_GRAY), WEST.getName());
        result.add(new ColorPanel(LIGHT_GRAY), EAST.getName());
        result.add(new ColorPanel(LIGHT_GRAY), SOUTH.getName());
        result.add(new ColorPanel(LIGHT_GRAY), NORTH.getName());
        result.add(colorButtonsPanel, CENTER.getName());

        return result;
    }
}
