package com.painting.application;

import com.painting.application.color.ColorLayout;
import com.painting.application.drawing.DrawingPanel;
import com.painting.application.menu.Header;
import com.painting.application.stroke.StrokeToolPanel;

import javax.swing.*;

import static com.painting.application.direction.Direction.*;

public class UI extends JFrame {
    private static final int UI_WIDTH = 1220;
    private static final int UI_HEIGHT = 820;

    public DrawingPanel drawingPanel;
    public ColorLayout colorLayout;

    UI() {
        super("Drawing application");

        drawingPanel = new DrawingPanel();
        colorLayout = new ColorLayout();

        add(new JScrollPane(drawingPanel), CENTER.getName());
        add(colorLayout, SOUTH.getName());
        add(new Header(), NORTH.getName());
        add(new VectorToolPanel(new StrokeToolPanel(5)), WEST.getName());

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setIconImage(getIcon().getImage());
        this.setSize(UI_WIDTH, UI_HEIGHT);
    }

    private static ImageIcon getIcon() {
        return new ImageIcon(UI.class.getResource("img/colors.jpg"));
    }
}
