package com.painting.application.color;

import com.painting.application.Application;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.painting.application.color.ColorLayout.selected;
import static com.painting.application.color.ColorLayout.selectedPanel;
import static javax.swing.JColorChooser.showDialog;

public class OnColorPressed extends MouseAdapter {
    @Override
    public void mousePressed(MouseEvent event) {
        {
            selectedPanel.setBackground(showDialog(Application.ui, "Color change", Application.ui.drawingPanel.brushColor));
            selected = selectedPanel.getBackground();
            Application.ui.drawingPanel.currentDecoratedInstrument.setColor(selectedPanel.getBackground());
            Application.ui.drawingPanel.setCurrentColor(selected);
        }
    }
}
