package com.scollay.neuralnetwork;

import java.awt.Graphics;
import java.util.Random;
import java.util.Vector;

public class Organism {

  final private static double MUTATION_RATE  = 0.1;
  final private static double MAX_MUTATION   = 0.3;

  final private static double CROSSOVER_RATE = 0;

  private NeuralNetwork       brain;
  private Vector<Double>      inputs = new Vector<Double>(), outputs = new Vector<Double>();
  private double              fitness        = 0;

  public Organism(NeuralNetwork brain) {
    this.brain = new NeuralNetwork(brain);
  }

  public Organism(int inputs, int[] hidden, int outputs) {
    this.brain = new NeuralNetwork(inputs, hidden, outputs);
  }

  public void update() {
  }
  public void draw(Graphics g) {}

  public Organism subClass() {
    return new Organism(0, new int[] { 0 }, 0);
  }
  public Organism subClass(NeuralNetwork brain) {
    return new Organism(brain);
  }

  public static Organism breed(Organism parent1, Organism parent2) {
    if (new Random().nextDouble() < CROSSOVER_RATE) {
      return parent1;
    }

    if (parent1.getBrain().getWeights().size() == parent2.getBrain().getWeights().size()) {
      int crossoverPoint = (int) (new Random().nextDouble() * parent1.getBrain().getWeights().size());
      Vector<Double> childWeights = new Vector<Double>();

      for (int i = 0; i < crossoverPoint; i++) {
        childWeights.add(parent1.getBrain().getWeights().get(i));
      }
      for (int i = crossoverPoint; i < parent1.getBrain().getWeights().size(); i++) {
        childWeights.add(parent2.getBrain().getWeights().get(i));
      }
      

      Organism child1 = parent1.subClass();
      child1.getBrain().setWeights(childWeights);
      child1.mutate();
      return child1;
    }
    else {

      return parent1;
    }
  }

  public void mutate() {
    for (int i = 0; i < this.brain.getWeights().size(); i++) {
      if (new Random().nextDouble() < MUTATION_RATE) {
        double newWeight = this.brain.getWeights().get(i) + ((new Random().nextDouble() - 0.5) * MAX_MUTATION);
        if(newWeight > 1){
          newWeight = 1;
        }
        else if(newWeight < -1){
          newWeight = -1;
        }
        this.brain.setWeights(i, newWeight);
      }
    }
  }

  public Vector<Organism> makeClones(int numCopies) {
    Vector<Organism> clones = new Vector<Organism>();

    for (int i = 0; i < numCopies; i++) {
      Organism resetOrganism = this.subClass(this.getBrain());
      clones.add(resetOrganism);
    }
    
    return clones;
  }

  public NeuralNetwork getBrain() {
    return brain;
  }

  public void setBrain(NeuralNetwork brain) {
    this.brain = brain;
  }

  public Vector<Double> getInputs() {
    return inputs;
  }

  public void setInputs(Vector<Double> inputs) {
    this.inputs = inputs;
  }

  public Vector<Double> getOutputs() {
    return outputs;
  }

  public void setOutputs(Vector<Double> outputs) {
    this.outputs = outputs;
  }

  public double getFitness() {
    return fitness;
  }

  public void setFitness(double fitness) {
    this.fitness = fitness;
  }
  
  public void addFitness(double fitness) {
    this.fitness += fitness;
  }

}
