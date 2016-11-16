package com.scollay.neuralnetwork;

import java.util.Random;
import java.util.Vector;

/**
 * Contains {@link weights} that are used to control signaling received from other Neurons, or direct input.
 * 
 * @param weights
 *          A {@link Vector} of {@link Double}s containing numbers ranging from -1 to +1.
 *
 */
public class Neuron {

  private final static double BIAS                = -1;
  private final static double ACTIVATION_RESPONSE = 1;
  private Vector<Double>      weights             = new Vector<Double>();

  /**
   * Constructs a Neuron with randomly generated weights.
   * 
   * @param numWeights
   *          Determines how many weights the Neuron has going into it.
   */
  public Neuron(int numWeights) {
    if (numWeights > 0) {
      for (int i = 0; i < numWeights + 1; i++) {
        this.weights.add((new Random().nextDouble() * 2) - 1);
      }
    }
    else {
      this.weights.add(0.0);
    }
  }

  /**
   * Takes inputs and produces the Neuron's output based on it's weights.
   * 
   * @param inputs
   *          The signals the Neuron is receiving (A double ranging from 0-1).
   * @return The summation of the products of inputs and weights minus the bias parsed through the <code>sigmoid()</code> function
   *         as a double.
   */
  public double getOutput(Vector<Double> inputs) {
    if (inputs.size() == this.weights.size() - 1) {
      double output = 0;
      for (int i = 0; i < inputs.size(); i++) {
        output += inputs.get(i) * this.weights.get(i);
      }
      output += BIAS * this.weights.get(this.weights.size()-1);
      return sigmoid(output);
    }
    System.out.println("ERROR: Neuron.product(Vector<Double>) - Input size does not match weight size.");
    return 0;
  }

  /**
   * The Sigmoid activation function. Maps any double input to a double output ranging from 0 to 1
   * 
   * @param input
   *          The summation of products of inputs and weights of a neuron minus the bias.
   * @return A double ranging from 0 to 1.
   */
  public double sigmoid(double input) {
    return 1 / (1 + (Math.exp(-input / ACTIVATION_RESPONSE)));
  }

  public Vector<Double> getWeights() {
    return this.weights;
  }

  public void setWeights(Vector<Double> weights) {
    this.weights = weights;
  }
}
