package com.painting.application;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Hashtable;

public class UndoAndRedo extends JFrame {
    private Hashtable<String, Point> newPoints;
    private Hashtable<String, Point> oldPoints = new Hashtable<String, Point>();
    private UndoManager undoManager = new UndoManager();
    private JButton undoButton = new JButton("Undo");
    private JButton redoButton = new JButton("Redo");
    private Point startPoint, endPoint;
    private Hashtable<String, Point> pointTable = new Hashtable<>();
    private PaintCanvas canvas = new PaintCanvas(pointTable);

    private UndoAndRedo() {
        super("Undo/Redo Demo");

        undoButton.setEnabled(false);
        redoButton.setEnabled(false);

        JPanel buttonPanel = new JPanel(new GridLayout());
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);

        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);

        canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startPoint = new Point(e.getPoint());
                pointTable.put("StartPoint", startPoint);

                undoManager.undoableEditHappened(new UndoableEditEvent(
                        canvas, new UndoablePaint(pointTable)));

                undoButton.setText(undoManager.getUndoPresentationName());
                redoButton.setText(undoManager.getRedoPresentationName());
                undoButton.setEnabled(undoManager.canUndo());
                redoButton.setEnabled(undoManager.canRedo());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                super.mouseReleased(e);
                endPoint = new Point(e.getPoint());
                pointTable.put("EndPoint", endPoint);

                undoManager.undoableEditHappened(new UndoableEditEvent(
                        canvas, new UndoablePaint(pointTable)));

                undoButton.setText(undoManager.getUndoPresentationName());
                redoButton.setText(undoManager.getRedoPresentationName());
                undoButton.setEnabled(undoManager.canUndo());
                redoButton.setEnabled(undoManager.canRedo());
                canvas.repaint();
            }
        });

        undoButton.addActionListener(e -> {
            try {
                undoManager.undo();
            } catch (CannotRedoException cre) {
                cre.printStackTrace();
            }
            canvas.repaint();
            undoButton.setEnabled(undoManager.canUndo());
            redoButton.setEnabled(undoManager.canRedo());
        });

        redoButton.addActionListener(e -> {
            try {
                undoManager.redo();
            } catch (CannotRedoException cre) {
                cre.printStackTrace();
            }
            canvas.repaint();
            undoButton.setEnabled(undoManager.canUndo());
            redoButton.setEnabled(undoManager.canRedo());
        });

        setSize(640, 480);
        setVisible(true);
    }

    public static void main(String argv[]) {
        new UndoAndRedo();
    }

    class PaintCanvas extends JPanel {
        private Hashtable<String, Point> points;

        PaintCanvas(Hashtable<String, Point> point) {
            super();
            points = point;
            setOpaque(true);
            setBackground(Color.white);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.black);

            for (int i = 0; i < points.size(); i++) {
                Point startPoint = pointTable.get("StartPoint");
                Point endPoint = pointTable.get("EndPoint");
                g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
            }
        }
    }

    class UndoablePaint extends AbstractUndoableEdit {
        private Enumeration<String> newString;

        UndoablePaint(Hashtable<String, Point> point) {
            newPoints = point;
            newString = newPoints.keys();
            oldPoints = newPoints;
        }

        public String getPresentationName() {
            return "Line Addition";
        }

        public void undo() {
            super.undo();
            while (newString.hasMoreElements()) {
                newPoints.remove(newString.nextElement());
            }
        }

        public void redo() {
            super.redo();
            pointTable = oldPoints;
        }
    }
}
