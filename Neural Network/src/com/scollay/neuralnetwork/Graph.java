package com.scollay.neuralnetwork;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

public class Graph {

  private static final int MARGIN     = 10;
  private static final int END_MARGIN = 10;

  public static void drawLineGraph(Graphics g, Vector<Double>[] xCoords, Vector<Double>[] yCoords, int x, int y, int width,
      int height) {
    drawLineAxis(g, x, y, width, height);
    drawLine(g, xCoords, yCoords, x, y, width, height);
  }

  public static void drawLineAxis(Graphics g, int x, int y, int width, int height) {
    g.setColor(new Color(0, 0, 0));
    g.drawLine(x + MARGIN, y, x + MARGIN, y + height);
    g.drawLine(x, y + height - MARGIN, x + width, y + height - MARGIN);
  }

  public static void drawLine(Graphics g, Vector<Double>[] xCoords, Vector<Double>[] yCoords, int x, int y, int width, int height) {
    double largestX = 0;
    double largestY = 0;
    for (int i = 0; i < xCoords.length; i++) {
      for (int j = 0; j < xCoords[i].size(); j++) {
        if (xCoords[i].get(j) > largestX) {
          largestX = xCoords[i].get(j);
        }
        if (yCoords[i].get(j) > largestY) {
          largestY = yCoords[i].get(j);
        }
      }
    }
    Color[] lineColor = new Color[]{new Color(255,0,0), new Color(0,0,255), new Color(0,255,0)};
    g.setColor(new Color(0, 0, 255));
    for (int i = 0; i < xCoords.length; i++) {
      g.setColor(lineColor[i]);
      for (int j = 0; j < xCoords[i].size() - 1; j++) {
        g.drawLine((int) (MARGIN + ((width - MARGIN - END_MARGIN) * (xCoords[i].get(j) / largestX))) + x,
            (int) ((height - MARGIN) - ((height - MARGIN  - END_MARGIN) * (yCoords[i].get(j) / largestY))) + y,
            (int) (MARGIN + ((width - MARGIN - END_MARGIN) * (xCoords[i].get(j + 1) / largestX))) + x,
            (int) ((height - MARGIN) - ((height - MARGIN  - END_MARGIN) * (yCoords[i].get(j + 1) / largestY))) + y);
      }
    }
    g.setColor(new Color(0, 0, 0));

    int numReadings = 6;
    NumberFormat formatter = new DecimalFormat("#0.00");
    for (int i = 0; i < numReadings; i++) {
      g.drawLine(x + MARGIN - 3, y + END_MARGIN + ((i) * (height - MARGIN - END_MARGIN) / (numReadings)), x
          + MARGIN, y + END_MARGIN + ((i) * (height - MARGIN - END_MARGIN) / (numReadings)));

      g.drawString(formatter.format((largestY / (numReadings)) * (numReadings - i)), x + MARGIN - 36, y
          + END_MARGIN + 4 + ((i) * (height - MARGIN - END_MARGIN) / (numReadings)));
    }
    for (int i = 0; i < numReadings; i++) {
      g.drawLine(MARGIN + x + ((i + 1) * (width - MARGIN - END_MARGIN) / (numReadings)), y + height - MARGIN,
          MARGIN + x + ((i + 1) * (width - MARGIN - END_MARGIN) / (numReadings)), y + height - MARGIN + 3);

      g.drawString(formatter.format((largestX / (numReadings)) * (i+1)), x + MARGIN - 12
          + ((i + 1) * (width - MARGIN - END_MARGIN) / (numReadings)), y + height - MARGIN + 20);
    }
  }
}
