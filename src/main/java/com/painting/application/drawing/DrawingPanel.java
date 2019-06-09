package com.painting.application.drawing;

import com.painting.application.instrument.Instrument;
import com.painting.application.instrument.InstrumentDecorator;
import com.painting.application.instrument.InstrumentFactory;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static com.painting.application.instrument.Instrument.Type.*;
import static com.painting.application.instrument.InstrumentFactory.anInstrument;
import static java.awt.Color.*;

public class DrawingPanel extends DefaultDrawingPanel {
    private static Graphics2D dragGraphics;
    public InstrumentDecorator currentDecoratedInstrument;
    public Instrument currentInstrument;

    private Image osi;
    public Color brushColor;

    private int osiWidth, osiHeight;
    private int mouseX, mouseY;
    private int prevX, prevY;
    private int startX, startY;
    private boolean inDrawingMode;
    private ArrayList<Integer> polX = new ArrayList<>();
    private ArrayList<Integer> polY = new ArrayList<>();

    private double zoomFactor = 1;
    private boolean zoomer = false;

    public DrawingPanel() {
        setBackground(WHITE);
        setPreferredSize(new Dimension(1024, 768));
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        brushColor = BLACK;
        currentInstrument = InstrumentFactory.anInstrument(PENCIL);
        currentDecoratedInstrument = new InstrumentDecorator(brushColor, 5, currentInstrument);
    }

    private void drawGraphics(Graphics2D graphics2D, Instrument instrument, int pointX1, int pointY1, int pointX2, int pointY2) {
        new DrawGraphicsCommand(this)
                .execute(graphics2D, instrument, pointX1, pointY1, pointX2, pointY2, currentDecoratedInstrument, polX, polY);
    }

    public void loadingVecDataToPaint(File file) {
        dragGraphics = (Graphics2D) osi.getGraphics();
        dragGraphics.setColor(brushColor);
        dragGraphics.setBackground(getBackground());

        if (file != null) {
            try {
                FileReader vecFileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(vecFileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {

                    String[] str = line.split(" ");

                    if (str[0].equals("LINE"))
                        currentInstrument.type = LINE;
                    if (str[0].equals("RECTANGLE"))
                        currentInstrument.type = RECTANGLE;
                    if (str[0].equals("FILLED-RECTANGLE"))
                        currentInstrument.type = FILLED_RECTANGLE;
                    if (str[0].equals("ELLIPSE"))
                        currentInstrument.type = ELLIPSE;
                    if (str[0].equals("FILLED-ELLIPSE"))
                        currentInstrument.type = FILLED_ELLIPSE;
                    if (str[0].equals("POLYGON"))
                        currentInstrument.type = POLYGON;
                    if (str[0].equals("FILLED_POLYGON"))
                        currentInstrument.type = FILLED_POLYGON;
                    if (str[0].equals("PLOT"))
                        currentInstrument.type = PLOT;

                    System.out.println(line);
                    drawGraphics(dragGraphics, currentInstrument, Integer.valueOf(str[1]), Integer.valueOf(str[2]), Integer.valueOf(str[3]), Integer.valueOf(str[4]));
                }

                vecFileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createOffScreenImage() {
        if (osi == null || osiWidth != getSize().width || osiHeight != getSize().height) {

            osi = null;
            osi = createImage(getSize().width, getSize().height);
            osiWidth = getSize().width;
            osiHeight = getSize().height;
            Graphics graphics = osi.getGraphics();
            graphics.setColor(getBackground());
            graphics.fillRect(0, 0, osiWidth, osiHeight);
            graphics.dispose();
        }
    }


    public void paintComponent(Graphics graphics) {
        createOffScreenImage();
        Graphics2D graphics2D = (Graphics2D) graphics;
        if (zoomer) {
            AffineTransform at = new AffineTransform();
            at.scale(zoomFactor, zoomFactor);

            graphics2D.transform(at);
            zoomer = false;
        }
        graphics.drawImage(osi, 0, 0, this);
        if (inDrawingMode &&
                currentInstrument.type != PENCIL &&
                currentInstrument.type != AIR_BRUSH &&
                currentInstrument.type != ERASER) {
            graphics.setColor(brushColor);
            drawGraphics(graphics2D, currentInstrument, startX, startY, mouseX, mouseY);
        }
    }


    private void repaintRectangle(int pointX1, int pointY1, int pointX2, int pointY2) {
        int x, y;
        int width, height;
        if (pointX2 >= pointX1) {
            x = pointX1;
            width = pointX2 - pointX1;
        } else {
            x = pointX2;
            width = pointX1 - pointX2;
        }
        if (pointY2 >= pointY1) {
            y = pointY1;
            height = pointY2 - pointY1;
        } else {
            y = pointY2;
            height = pointY1 - pointY2;
        }
        repaint(x, y, width + 1, height + 1);
    }

    public void setOSImage(BufferedImage image) {
        osi = image;
        repaint();
    }

    public void setImage(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        osi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        osi = createImage(w, h);
        osiWidth = getSize().width;
        osiHeight = getSize().height;
        repaint();
        Graphics graphics = osi.getGraphics();
        graphics.setColor(getBackground());
        graphics.fillRect(0, 0, osiWidth, osiHeight);
        graphics.dispose();
    }

    public void clearImage(BufferedImage image) {
        Graphics2D g = image.createGraphics();
        g.setColor(white);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
        repaint();
    }

    private Color getCurrentColor() {
        return currentInstrument.type != ERASER ? currentDecoratedInstrument.getColor() : getBackground();
    }

    public void setCurrentColor(Color color) {
        brushColor = color;
        currentDecoratedInstrument.setColor(color);
    }


    public void mousePressed(MouseEvent event) {
        if (inDrawingMode)
            return;

        prevX = startX = event.getX();
        prevY = startY = event.getY();

        dragGraphics = (Graphics2D) osi.getGraphics();
        dragGraphics.setColor(getCurrentColor());
        dragGraphics.setBackground(getBackground());

        inDrawingMode = true;
    }


    public void mouseReleased(MouseEvent evt) {
        if (!inDrawingMode)
            return;

        inDrawingMode = false;
        mouseX = evt.getX();
        mouseY = evt.getY();

        if (currentInstrument.type != PENCIL
                && currentInstrument.type != AIR_BRUSH
                && currentInstrument.type != ERASER) {
            repaintRectangle(startX, startY, prevX, prevY);
            if (mouseX != startX && mouseY != startY) {


                drawGraphics(dragGraphics, currentInstrument, startX, startY, mouseX, mouseY);
                repaintRectangle(startX, startY, mouseX, mouseY);
                try {
                    new Drawing(currentInstrument.type, startX, startY, mouseX, mouseY).writeToFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (currentInstrument.type == PLOT) {
                System.out.println("Inside mouse released");
                drawGraphics(dragGraphics, InstrumentFactory.anInstrument(PLOT), mouseX, mouseY, mouseX + 1, mouseY + 1);
            }
        }


        dragGraphics.dispose();
        dragGraphics = null;

        repaint();
    }


    public void mouseDragged(MouseEvent evt) {
        if (!inDrawingMode)
            return;

        mouseX = evt.getX();
        mouseY = evt.getY();

        if (currentInstrument.type == PENCIL) {
            drawGraphics(dragGraphics, anInstrument(LINE), prevX, prevY, mouseX, mouseY);
            repaintRectangle(prevX, prevY, mouseX, mouseY);
        } else if (currentInstrument.type == ERASER) {
            drawGraphics(dragGraphics, anInstrument(LINE), prevX, prevY, mouseX, mouseY);
            repaintRectangle(prevX, prevY, mouseX, mouseY);
        } else if (currentInstrument.type == AIR_BRUSH) {
            drawGraphics(dragGraphics, anInstrument(AIR_BRUSH), prevX, prevY, mouseX, mouseY);
            repaintRectangle(prevX, prevY, mouseX, mouseY);
        } else {
            repaintRectangle(startX, startY, prevX, prevY);
            repaintRectangle(startX, startY, mouseX, mouseY);

        }

        prevX = mouseX;
        prevY = mouseY;


        repaint();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (currentInstrument.type == POLYGON || currentInstrument.type == FILLED_POLYGON) {
            int x = e.getX();
            int y = e.getY();

            polX.add(x);
            polY.add(y);

            System.out.println(polX);
            System.out.println(polY);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            System.out.println("Space");

        }
    }

    public void zoomIn() {
        zoomFactor *= 1.1;
        zoomer = true;
        repaint();
    }

    public void zoomOut() {
        zoomFactor /= 1.1;
        zoomer = true;
        repaint();
    }

    public void drawPolygon() {
        dragGraphics = (Graphics2D) osi.getGraphics();
        resetCoordinates();

        if (currentInstrument.type == POLYGON) {
            drawGraphics(dragGraphics, anInstrument(POLYGON), prevX, prevY, mouseX, mouseY);
        }
        if (currentInstrument.type == FILLED_POLYGON) {
            drawGraphics(dragGraphics, anInstrument(FILLED_POLYGON), prevX, prevY, mouseX, mouseY);
        }
    }

    public void drawPlot() {
        resetCoordinates();
        dragGraphics = (Graphics2D) osi.getGraphics();

        currentInstrument.type = PLOT;
    }

    private void resetCoordinates() {
        mouseX = 0;
        mouseY = 0;
    }
}