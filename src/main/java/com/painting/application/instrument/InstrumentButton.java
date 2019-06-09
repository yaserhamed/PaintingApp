package com.painting.application.instrument;

import com.painting.application.Application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstrumentButton extends JButton implements ActionListener {

    private Instrument instrument;

    public InstrumentButton(Icon icon, Instrument instrument) {
        JLabel label = new JLabel(icon);
        setLayout(new BorderLayout());
        add(label);
        this.instrument = instrument;
        addActionListener(this);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
    }

    public void actionPerformed(ActionEvent event) {
        Application.ui.drawingPanel.currentInstrument = instrument;
        Application.ui.repaint();
    }
}
