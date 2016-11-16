package com.scollay.neuralnetwork;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GraphRender extends JPanel implements ActionListener {
	Timer time = new Timer(10, this);

	public GraphRender() {
		time.start();
		this.setBackground(new Color(255,255,255));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graph.drawLineGraph(g, Application.getxCoords(), Application.getyCoords(), 50, 20, 790, 600);
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
