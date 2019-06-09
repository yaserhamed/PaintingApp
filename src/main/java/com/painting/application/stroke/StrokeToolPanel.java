package com.painting.application.stroke;

import com.painting.application.Application;
import com.painting.application.direction.Direction;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;


public class StrokeToolPanel extends JPanel {

    private JSlider strokeSlider;

    public StrokeToolPanel(int stroke) {
        setPreferredSize(new Dimension(50, 256));
        setBackground(Color.LIGHT_GRAY);
        setLayout(new FlowLayout());

        strokeSlider = new JSlider(0, 0, 16, 5);
        strokeSlider.setPreferredSize(new Dimension(200, 20));
        strokeSlider.setPaintTicks(false);
        strokeSlider.setMajorTickSpacing(1);
        strokeSlider.setValue(stroke);
        strokeSlider.revalidate();

        SlideChangeListener listener = new SlideChangeListener();
        strokeSlider.addChangeListener(listener);
        JPanel strokePanel = new JPanel();
        strokePanel.setBackground(Color.WHITE);
        strokePanel.setPreferredSize(new Dimension(200, 100));
        strokePanel.setLayout(new FlowLayout());
        strokePanel.add(new StrokePanel(), Direction.SOUTH);
        add(strokePanel);
        add(strokeSlider);
    }


    private class SlideChangeListener implements ChangeListener {
        public void stateChanged(ChangeEvent event) {
            Application.ui.drawingPanel.currentDecoratedInstrument.setFontWidth(strokeSlider.getValue());
            repaint();
        }
    }
}
