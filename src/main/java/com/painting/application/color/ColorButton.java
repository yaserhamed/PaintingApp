package com.painting.application.color;

import com.painting.application.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class ColorButton extends JPanel {
    boolean selected;

    ColorButton(Color color) {
        selected = false;
        setBackground(color);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Application.ui.colorLayout.unselect();
                selected = true;
                Application.ui.drawingPanel.setCurrentColor(color);
                ColorLayout.selectedPanel.setBackground(color);
                Application.ui.repaint();
            }
        });
    }
}
