package com.scollay.neuralnetwork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Render extends JPanel implements ActionListener {

  private static final long serialVersionUID = 1L;
  Timer                     time             = new Timer(10, this);
  private GenePool          genePool;

  int                       x                = 0;

  public Render(GenePool genePool) {
    this.genePool = genePool;
    time.start();
    this.setBackground(new Color(255, 255, 255));
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.translate(Application.SIM_X, Application.SIM_Y);
    g.setColor(new Color(0, 0, 0));
    g.drawRect(0, 0, Application.SIM_WIDTH, Application.SIM_HEIGHT);

    Minesweeper.drawFood(g);
    for (int i = 0; i < genePool.getOrganisms().size(); i++) {
      genePool.getOrganisms().get(i).draw(g);

    }
    g.translate(-Application.SIM_X, -Application.SIM_Y);

    //genePool.sort();
    for (int i = 0; i < genePool.getOrganisms().size(); i++) {
      genePool.getOrganisms().get(i).getBrain().draw(g, Application.SIM_X + Application.SIM_WIDTH + 10 + (90 * (i % 4)),
          Application.SIM_Y + (90 * (i / 4)), 80, 80);
    }
    
    
    Graph.drawLineGraph(g, Application.getxCoords(), Application.getyCoords(), Application.SIM_X+Application.SIM_WIDTH + 30, Application.SIM_Y + Application.SIM_HEIGHT - 190, 340, 180);

  }

  public void actionPerformed(ActionEvent e) {
    x++;
    genePool.timeLapse(1);
    if (x >= 500) {
      genePool.timeLapse(9500);
      updateGraph();
      genePool.update();
      x = 0;
    }
    repaint();
  }

  public void updateGraph() {
    Application.getxCoords()[0].add((double) Application.getxCoords()[0].size());
    Application.getxCoords()[1].add((double) Application.getxCoords()[1].size());
    Application.getyCoords()[0].add(genePool.getBestFitness());
    Application.getyCoords()[1].add(genePool.getAverageFitness());
  }

}
