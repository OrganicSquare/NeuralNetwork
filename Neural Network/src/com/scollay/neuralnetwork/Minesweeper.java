package com.scollay.neuralnetwork;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Vector;

public class Minesweeper extends Organism {

  final private static int    NUMBER_OF_INPUTS  = 2;
  final private static int    NUMBER_OF_OUTPUTS = 2;

  final private static double SPEED             = 2;
  final private static double ROTATION_SPEED    = Math.PI;

  private double              x, y, rotation, radius = 2;

  private static Vector<Food> food              = new Vector<Food>();

  public Minesweeper() {
    super(NUMBER_OF_INPUTS, new int[] { 4 }, NUMBER_OF_OUTPUTS);
    this.x = new Random().nextDouble() * Application.SIM_WIDTH;
    this.y = new Random().nextDouble() * Application.SIM_HEIGHT;
    this.rotation = new Random().nextDouble() * Math.PI * 2;
  }

  public Minesweeper(int inputs, int[] hidden, int outputs) {
    super(inputs, hidden, outputs);
    this.x = new Random().nextDouble() * Application.SIM_WIDTH;
    this.y = new Random().nextDouble() * Application.SIM_HEIGHT;
    this.rotation = new Random().nextDouble() * Math.PI * 2;
  }

  public Minesweeper(NeuralNetwork brain) {
    super(brain);
    this.x = new Random().nextDouble() * Application.SIM_WIDTH;
    this.y = new Random().nextDouble() * Application.SIM_HEIGHT;
    this.rotation = new Random().nextDouble() * Math.PI * 2;
  }

  public static void initialiseFood(int numFood) {
    for (int i = 0; i < numFood; i++) {
      food.add(new Food());
    }
  }

  public void update() {
    this.getClosestFood();

    this.setOutputs(this.getBrain().update(this.getInputs()));

    double leftTrack = this.getOutputs().get(0);
    double rightTrack = this.getOutputs().get(1);

    double rotForce = leftTrack - rightTrack;

    if (rotForce > ROTATION_SPEED) {
      rotForce = ROTATION_SPEED;
    }
    if (rotForce < -ROTATION_SPEED) {
      rotForce = -ROTATION_SPEED;
    }

    rotation += rotForce;

    if (rotation < 0) {
      rotation += Math.PI * 2;
    }

    rotation %= Math.PI * 2;

    this.x += SPEED * Math.sin(rotation);
    this.y -= SPEED * Math.cos(rotation);

    if (this.x < 0) {
      this.x = Application.SIM_WIDTH;
    }
    if (this.x > Application.SIM_WIDTH) {
      this.x = 0;
    }
    if (this.y > Application.SIM_HEIGHT) {
      this.y = 0;
    }
    if (this.y < 0) {
      this.y = Application.SIM_HEIGHT;
    }

    for (int i = 0; i < food.size(); i++) {
      food.get(i).testCollision(this);
    }
  }

  public Food getClosestFood() {
    double lowestDistance = Math.pow(2, 64);
    double lowestAngle = Math.pow(2, 64);
    int lowestFoodIndex = 0;
    for (int i = 0; i < food.size(); i++) {
      if (Geom.distance(this.getX(), this.getY(), food.get(i).getX(), food.get(i).getY()) < lowestDistance) {
        lowestDistance = Geom.distance(this.getX(), this.getY(), food.get(i).getX(), food.get(i).getY());
        lowestAngle = Geom.angle(this.getX(), this.getY(), food.get(i).getX(), food.get(i).getY());
        lowestFoodIndex = i;
      }
    }
    
    double rotationInput = Math.toRadians(lowestAngle) - this.getRotation();
    if (rotationInput < 0) {
      if (Math.abs(rotationInput) > Math.PI) {
        rotationInput = (Math.PI * 2 - Math.abs(rotationInput));
      }
    }
    else {
      if (Math.abs(rotationInput) > Math.PI) {
        rotationInput = -(Math.PI * 2 - Math.abs(rotationInput));
      }
    }

    this.getInputs().clear();
    this.getInputs().add(lowestDistance / Geom.distance(0, 0, 100, 100)); // CHANGE
    this.getInputs().add((rotationInput) / (Math.PI));

    return food.get(lowestFoodIndex);

  }

  public Organism subClass() {
    return new Minesweeper();
  }

  public Organism subClass(NeuralNetwork brain) {
    return new Minesweeper(brain);
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

  public double getRotation() {
    return rotation;
  }

  public void setRotation(double rotation) {
    this.rotation = rotation;
  }

  public double getRadius() {
    return radius;
  }

  public void setRadius(double radius) {
    this.radius = radius;
  }

  public void draw(Graphics g) {
    g.setColor(new Color(255, 255, 255));
    g.fillOval((int) (this.x - this.radius), (int) (this.y - this.radius), (int) (this.radius * 2), (int) (this.radius * 2));
    g.setColor(new Color(0, 0, 0));
    g.drawOval((int) (this.x - this.radius), (int) (this.y - this.radius), (int) (this.radius * 2), (int) (this.radius * 2));
    g.drawLine((int) this.getX(), (int) this.getY(), (int) (this.getX() + this.getRadius() * Math.sin(this.getRotation())),
        (int) (this.getY() - this.getRadius() * Math.cos(this.getRotation())));

  }

  public static void drawFood(Graphics g) {
    g.setColor(new Color(0, 150, 0));
    for (int i = 0; i < food.size(); i++) {
      g.fillOval((int) (food.get(i).getX() - food.get(i).getRadius() * 2), (int) (food.get(i).getY() - food.get(i).getRadius() * 2),
          (int) (food.get(i).getRadius() * 2), (int) (food.get(i).getRadius() * 2));
    }
  }

}
