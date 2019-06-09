package com.painting.application;

import com.painting.application.color.ColorLayout;
import com.painting.application.instrument.InstrumentButton;
import com.painting.application.stroke.StrokeToolPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import static com.painting.application.direction.Direction.*;
import static com.painting.application.instrument.Instrument.Type.*;
import static com.painting.application.instrument.InstrumentFactory.anInstrument;

@SuppressWarnings("ALL")
public class VectorToolPanel extends JPanel {
    public StrokeToolPanel strokeToolPanel;
    protected InstrumentButton instrumentButtons[];
    private JComboBox fillerType;
    private Icon pencil = new ImageIcon(aResource("img/pencil.png"));

    private Icon polygon = new ImageIcon(aResource("img/polygon.png"));
    private Icon filledPolygon = new ImageIcon(aResource("img/filled-polygon.png"));
    private Icon oval = new ImageIcon(aResource("img/oval.png"));
    private Icon filledOval = new ImageIcon(aResource("img/filled-oval.png"));
    private Icon rectangle = new ImageIcon(aResource("img/rectangle.png"));
    private Icon filledRectangle = new ImageIcon(aResource("img/filled-rectangle.png"));
    private Icon lineTool = new ImageIcon(aResource("img/line.png"));
    private Icon paintBrush = new ImageIcon(aResource("img/paint-brush.png"));
    private Icon eraser = new ImageIcon(aResource("img/eraser.png"));
    private JPanel toolPanel = new JPanel();
    private InstrumentButton colorPickerButton;

    public VectorToolPanel(StrokeToolPanel strokeToolPanel) {
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(250, 0));
        setLayout(new BorderLayout(8, 8));

        toolPanel.setLayout(new GridLayout(5, 2));
        toolPanel.setPreferredSize(new Dimension(300, 350));
        this.strokeToolPanel = strokeToolPanel;
        instrumentButtons = new InstrumentButton[10];

        Icon colorPicker = new ImageIcon(aResource("img/colors.jpg"));
        colorPickerButton = new InstrumentButton(colorPicker, anInstrument(PENCIL));
        colorPickerButton.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent event) {
                {
                    ColorLayout.selectedPanel.setBackground(JColorChooser.showDialog(Application.ui, "Change Color", Application.ui.drawingPanel.brushColor));
                    ColorLayout.selected = ColorLayout.selectedPanel.getBackground();
                    Application.ui.drawingPanel.currentDecoratedInstrument.setColor(ColorLayout.selectedPanel.getBackground());
                    Application.ui.drawingPanel.setCurrentColor(ColorLayout.selected);
                }
            }
        });


        addInstruments();
        for (InstrumentButton instrumentButton : instrumentButtons) {
            toolPanel.add(instrumentButton);
        }

        add(toolPanel, NORTH.getName());
        add(strokeToolPanel, SOUTH.getName());
    }

    private URL aResource(String path) {
        return getClass().getResource(path);
    }

    private void addInstruments() {
        instrumentButtons[0] = new InstrumentButton(pencil, anInstrument(PENCIL));
        instrumentButtons[1] = new InstrumentButton(paintBrush, anInstrument(AIR_BRUSH));
        instrumentButtons[2] = new InstrumentButton(eraser, anInstrument(ERASER));
        instrumentButtons[3] = colorPickerButton;
        instrumentButtons[4] = new InstrumentButton(lineTool, anInstrument(LINE));
        instrumentButtons[5] = new InstrumentButton(oval, anInstrument(ELLIPSE));
        instrumentButtons[6] = new InstrumentButton(polygon, anInstrument(POLYGON));
        instrumentButtons[7] = new InstrumentButton(rectangle, anInstrument(RECTANGLE));
        instrumentButtons[8] = new InstrumentButton(filledOval, anInstrument(FILLED_ELLIPSE));
        instrumentButtons[9] = new InstrumentButton(filledRectangle, anInstrument(FILLED_RECTANGLE));
    }
}
