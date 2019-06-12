package com.painting.application.menu;

import com.painting.application.Application;



import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Logger;

public class Header extends JMenuBar {
    private static final Logger LOGGER = Logger.getLogger(Header.class.getName());
    private JMenuItem quit;
    private JMenuItem newFile;
    private JMenuItem openFile;
    private JMenuItem saveFile;
    private JMenuItem zoomin;
    private JMenuItem zoomout;
    private JMenuItem polygon;
    private JMenuItem plot;
    private JFileChooser fileChooser = null;
    private MenuOptionsHandler menuOptionsHandler = new MenuOptionsHandler();

    public Header() {
        JMenu edit = new JMenu("Edit");
        JMenu draw = new JMenu("Draw");

        JMenuItem undo = new JMenuItem("Undo");
        undo.addActionListener(menuOptionsHandler);

        polygon = new JMenuItem("Polygon");
        polygon.addActionListener(menuOptionsHandler);

        plot = new JMenuItem("Plot");
        plot.addActionListener(menuOptionsHandler);

        edit.add(undo);
        draw.add(polygon);
        draw.add(plot);
        add(aFileMenu());
        add(aZoomMenu());
        add(draw);
        add(edit);
    }

    private JMenu aZoomMenu() {
        JMenu zoomMenu = new JMenu("Zoom");

        zoomin = new JMenuItem("Zoom-In");
        zoomin.addActionListener(menuOptionsHandler);

        zoomout = new JMenuItem("Zoom-out");
        zoomout.addActionListener(menuOptionsHandler);

        zoomMenu.add(zoomin);
        zoomMenu.add(zoomout);

        return zoomMenu;
    }

    private JMenu aFileMenu() {
        JMenuItem saveVec = new JMenuItem("Save As VEC");
        saveVec.addActionListener(menuOptionsHandler);

        newFile = new JMenuItem("New File");
        newFile.addActionListener(menuOptionsHandler);

        openFile = new JMenuItem("Open File");
        openFile.addActionListener(menuOptionsHandler);

        saveFile = new JMenuItem("Save File");
        saveFile.addActionListener(menuOptionsHandler);

        quit = new JMenuItem("Exit");
        quit.addActionListener(menuOptionsHandler);

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.addSeparator();
        fileMenu.add(quit);
        fileMenu.add(saveVec);

        return fileMenu;
    }

    private static BufferedImage getScreenShot(Component component) {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());
        return image;
    }

    private JFileChooser getFileChooser() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser();
        }

        return fileChooser;
    }

    private class MenuOptionsHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == quit) {
                Application.ui.dispose();
                System.exit(0);
            }

            if (event.getSource() == newFile) {
                BufferedImage bi = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB);
                Application.ui.drawingPanel.clearImage(bi);
                Application.ui.drawingPanel.setImage(bi);
            }
            if (event.getSource() == saveFile) {
                saveToFile();
            }
            if (event.getSource() == openFile) {
                openFile();
            }
            if (event.getSource() == zoomin) {
                Application.ui.drawingPanel.zoomIn();
            }
            if (event.getSource() == zoomout) {
                Application.ui.drawingPanel.zoomOut();
            }

            if (event.getSource() == polygon) {
                Application.ui.drawingPanel.drawPolygon();
            }
            if (event.getSource() == plot) {
                Application.ui.drawingPanel.drawPlot();
            }
        }

        private void openFile() {
            JFileChooser ch = getFileChooser();
            int result = ch.showOpenDialog(Application.ui.drawingPanel);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File vecfile = ch.getSelectedFile();
                    System.out.println(vecfile.getAbsolutePath());
                    Application.ui.drawingPanel.loadingVecDataToPaint(vecfile);
                    Application.ui.drawingPanel.setOSImage(ImageIO.read(ch.getSelectedFile()));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Could not open file");
                }
            }
        }

        private void saveToFile() {
            JFileChooser jFileChooser = getFileChooser();
            int result = jFileChooser.showSaveDialog(Application.ui.drawingPanel);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File pngFile = jFileChooser.getSelectedFile();
                    File bpmFile = jFileChooser.getSelectedFile();
                    pngFile = new File(pngFile.getAbsolutePath() + ".png");
                    bpmFile = new File(bpmFile.getAbsolutePath() + ".bmp");
                    File saveVec = new File(jFileChooser.getSelectedFile().getAbsolutePath() + ".vec");
                    BufferedImage img = getScreenShot(Application.ui.drawingPanel);
                    ImageIO.write(img, "png", pngFile);
                    ImageIO.write(img, "bmp", bpmFile);
                    File tempFile = new File("TempOutput.vec");
                    if (tempFile.exists()) {
                        try {
                            FileReader vecFileReader = new FileReader("TempOutput.vec");
                            BufferedReader bufferedReader = new BufferedReader(vecFileReader);
                            StringBuffer stringBuffer = new StringBuffer();
                            String line;
                            BufferedWriter writer;
                            while ((line = bufferedReader.readLine()) != null) {
                                writer = new BufferedWriter(
                                        new FileWriter(saveVec, true));
                                writer.append(line);
                                writer.newLine();
                                writer.flush();
                                writer.close();
                            }
                            vecFileReader.close();
                            LOGGER.info("File Data:- " + stringBuffer.toString());
                        } catch (IOException e) {
                            LOGGER.info("File Reader Error:- " + e);
                        }
                    } else {
                        LOGGER.info("File not Found!!");
                    }
                    if (tempFile.delete()) {
                        LOGGER.info("Temp File deleted successfully");
                    } else {
                        LOGGER.info("Failed to delete the file");
                    }
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(null, "Could not save the file");
                }
            }
        }
    }
}
