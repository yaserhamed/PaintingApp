package com.painting.application.drawing;

import com.painting.application.instrument.Instrument;
import com.painting.application.instrument.InstrumentDecorator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static com.painting.application.instrument.Instrument.Type.*;
import static com.painting.application.instrument.Instrument.Type.FILLED_POLYGON;
import static com.painting.application.instrument.Instrument.Type.PLOT;

public class DrawGraphicsCommand {
    private final DrawingPanel panel;

    public DrawGraphicsCommand(DrawingPanel panel) {
        this.panel = panel;
    }

    public void execute(Graphics2D graphics2D, Instrument instrument,
                              int pointX1, int pointY1,
                              int pointX2, int pointY2,
                              InstrumentDecorator instrumentDecorator,
                              ArrayList<Integer> polX,
                              ArrayList<Integer> polY) {
        if (instrument.type == LINE) {
            System.out.println("Inside Line instrument :" + pointX1 + " " + " " + pointY1 + " " + pointX2 + " " + pointY2);
            graphics2D.setStroke(new BasicStroke(instrumentDecorator.fontWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics2D.drawLine(pointX1, pointY1, pointX2, pointY2);
            panel.repaint();
            return;
        }

        if (instrument.type == AIR_BRUSH) {
            Random rand = new Random();

            int[][] brushPoints = new int[(instrumentDecorator.fontWidth * instrumentDecorator.fontWidth) / 10][2];
            for (int i = 0; i < (instrumentDecorator.fontWidth * instrumentDecorator.fontWidth) / 10; i++) {
                int pts[] = new int[2];
                pts[0] = rand.nextInt(instrumentDecorator.fontWidth);
                pts[1] = rand.nextInt(instrumentDecorator.fontWidth);
                graphics2D.drawRect(pointX1 + pts[0], pointY1 + pts[1], 1, 1);
                brushPoints[i] = pts;
            }
            panel.repaint();
        }

        int positionX;
        int positionY;
        int weight, height;


        if (pointX1 >= pointX2) {
            positionX = pointX2;
            weight = pointX1 - pointX2;
        } else {
            positionX = pointX1;
            weight = pointX2 - pointX1;
        }
        if (pointY1 >= pointY2) {
            positionY = pointY2;
            height = pointY1 - pointY2;
        } else {
            positionY = pointY1;
            height = pointY2 - pointY1;
        }

        if (instrument.type == RECTANGLE) {
            graphics2D.setStroke(new BasicStroke(instrumentDecorator.fontWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics2D.drawRect(positionX, positionY, weight, height);
            panel.repaint();
            return;
        }
        if (instrument.type == POLYGON) {

            graphics2D.setStroke(new BasicStroke(instrumentDecorator.fontWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            Object objx[] = polX.toArray();
            Object objy[] = polY.toArray();

            int[] xArr = new int[objx.length];
            int[] yArr = new int[objx.length];


            for (int i = 0; i < objx.length; i++) {
                xArr[i] = (int) objx[i];
                yArr[i] = (int) objy[i];
            }
            graphics2D.drawPolygon(xArr, yArr, xArr.length);
            panel.repaint();                                    
            return;
        }
        
        if (instrument.type == ROUND_RECTANGLE) {
	        graphics2D.setStroke(new BasicStroke(instrumentDecorator.fontWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	        graphics2D.drawRoundRect(positionX, positionY, weight, height, 20, 20);    
	        panel.repaint();   
            return;
        }

        if (instrument.type == ELLIPSE) {
            graphics2D.setStroke(new BasicStroke(instrumentDecorator.fontWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics2D.drawOval(positionX, positionY, weight, height);
            panel.repaint();
            return;
        }

        if (instrument.type == FILLED_ELLIPSE) {
            graphics2D.fillOval(positionX, positionY, weight, height);
            panel.repaint();
            return;
        }

        if (instrument.type == FILLED_RECTANGLE) {
            graphics2D.fillRect(positionX, positionY, weight, height);
            panel.repaint();
            return;
        }

        if (instrument.type == FILLED_POLYGON) {
            Object objx[] = polX.toArray();
            Object objy[] = polY.toArray();

            int[] integerArrayx = new int[objx.length];
            int[] integerArrayy = new int[objx.length];


            for (int i = 0; i < objx.length; i++) {
                integerArrayx[i] = (int) objx[i];
                integerArrayy[i] = (int) objy[i];
            }
            graphics2D.fillPolygon(integerArrayx, integerArrayy, integerArrayx.length);
            panel.repaint();
            return;
        }
        if (instrument.type == PLOT) {
            graphics2D.setStroke(new BasicStroke(instrumentDecorator.fontWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics2D.drawLine(pointX1, pointY1, pointX2, pointY2);
            panel.repaint();
        }
    }
}
