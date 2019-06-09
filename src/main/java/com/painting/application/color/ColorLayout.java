package com.painting.application.color;

import javax.swing.*;
import java.awt.*;

import static com.painting.application.direction.Direction.CENTER;
import static com.painting.application.direction.Direction.WEST;
import static java.awt.Color.LIGHT_GRAY;

public class ColorLayout extends JPanel {
    private static final int SELECTED_PANEL_WIDTH = 120;
    private static final int SELECTED_PANEL_HEIGHT = 90;

    public static JPanel selectedPanel = new JPanel();
    public static Color selected;
    private ColorButton colorButtons[];

    public ColorLayout() {
        setBackground(LIGHT_GRAY);
        setLayout(new BorderLayout());
        Color[] colors = new Color[]{Color.BLACK, Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW};
        selected = colors[0];

        selectedPanel.setPreferredSize(new Dimension(SELECTED_PANEL_WIDTH, SELECTED_PANEL_HEIGHT));
        selectedPanel.addMouseListener(new OnColorPressed());
        JPanel colorButtonsGrid = new JPanel();
        colorButtonsGrid.setBackground(LIGHT_GRAY);
        colorButtonsGrid.setLayout(new GridLayout(1, 5, 1, 1));
        colorButtons = new ColorButton[colors.length];
        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i] = new ColorButton(colors[i]);
            colorButtonsGrid.add(colorButtons[i]);
        }

        ColorPanel colorButtonsPanel = new ColorPanel(LIGHT_GRAY);
        colorButtonsPanel.setLayout(new BorderLayout(6, 6));
        colorButtonsPanel.add(selectedPanel, WEST.getName());
        colorButtonsPanel.add(colorButtonsGrid, CENTER.getName());
        add(ColorButtonPanelFactory.create(colorButtonsPanel), CENTER.getName());
    }


    void unselect() {
        for (ColorButton colorButton : colorButtons) colorButton.selected = false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}