package com.scollay.neuralnetwork;

import java.util.Random;

public class Food {

  private double              x, y, radius = 2;

  public Food() {
    this.x = new Random().nextDouble() * Application.SIM_WIDTH;
    this.y = new Random().nextDouble() * Application.SIM_HEIGHT;
  }

  public boolean testCollision(Minesweeper minesweeper) {
    if (Geom.distance(this.x, this.y, minesweeper.getX(), minesweeper.getY()) < Math.abs(this.radius + minesweeper.getRadius())) {
      minesweeper.addFitness(1.0);
      this.reset();
      return true;
    }
    return false;
  }

  public void reset() {
    this.x = new Random().nextDouble() * Application.SIM_WIDTH;
    this.y = new Random().nextDouble() * Application.SIM_HEIGHT;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getRadius() {
    return this.radius;
  }

}
