package com.scollay.neuralnetwork;

import java.util.Vector;

import javax.swing.JFrame;

public class Application {

  final static private int        SCREEN_WIDTH    = 800;
  final static private int        SCREEN_HEIGHT   = 769;

  final static public int         SIM_X           = 10;
  final static public int         SIM_Y           = 10;

  final static public int         SIM_WIDTH       = 500;
  final static public int         SIM_HEIGHT      = 530;

  final static private int        POPULATION_SIZE = 10;

  private static Vector<Double>[] xCoords         = new Vector[] { new Vector<Double>(), new Vector<Double>() },
      yCoords = new Vector[] { new Vector<Double>(), new Vector<Double>() };

  public static void main(String args[]) {
    Vector<Organism> population = new Vector<Organism>();
    Minesweeper.initialiseFood(20);
    for (int i = 0; i < POPULATION_SIZE; i++) {
      population.add(new Minesweeper());
    }
    GenePool genePool = new GenePool(population);

    for (int i = 0; i < 0; i++) {
      genePool.timeLapse(20000);
      Application.getxCoords()[0].add((double) Application.getxCoords()[0].size());
      Application.getxCoords()[1].add((double) Application.getxCoords()[1].size());
      Application.getyCoords()[0].add(genePool.getBestFitness());
      Application.getyCoords()[1].add(genePool.getAverageFitness());
      genePool.update();
      System.out.println(i);
    }

    JFrame frame = new JFrame("Neural Network");
    Render panel = new Render(genePool);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT + (SIM_Y * 2));
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.add(panel);
    frame.setVisible(true);

  }

  public static Vector<Double>[] getxCoords() {
    return xCoords;
  }

  public static void setxCoords(Vector<Double>[] xCoords) {
    Application.xCoords = xCoords;
  }

  public static Vector<Double>[] getyCoords() {
    return yCoords;
  }

  public static void setyCoords(Vector<Double>[] yCoords) {
    Application.yCoords = yCoords;
  }

}
