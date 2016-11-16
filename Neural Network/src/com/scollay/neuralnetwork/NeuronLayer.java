package com.scollay.neuralnetwork;

import java.util.Vector;

/**
 * A layer of {@link Neuron}s these {@link Neuron}s explicitly do not interact with one another. A NeuronLayer can be used to
 * represent either input neurons, hidden neurons, or output neurons.
 * 
 * @param neurons
 *          A {@link Vector} of {@link Neuron}s used to represent one layer of {@link Neuron}s
 */
public class NeuronLayer {

  private Vector<Neuron> neurons = new Vector<Neuron>();

  /**
   * Constructs a layer of neurons.
   * 
   * @param numNeurons
   *          The number of neurons to be contained in the layer.
   * @param numWeights
   *          The number of weights to give each neuron.
   */
  public NeuronLayer(int numNeurons, int numWeights) {
    for (int i = 0; i < numNeurons; i++) {
      this.neurons.add(new Neuron(numWeights));
    }
  }

  /**
   * Retrieves all of the weights from all of the {@link Neuron}s in the NeuronLayer
   * 
   * @return A {@link Vector} of {@link Double}s containing all of the weights contained in the neurons.
   */
  public Vector<Double> getWeights() {
    Vector<Double> weights = new Vector<Double>();
    for (int i = 0; i < this.neurons.size(); i++) {
      for (int j = 0; j < this.neurons.get(i).getWeights().size(); j++) {
        weights.add(this.neurons.get(i).getWeights().get(j));
      }
    }
    return weights;
  }

  public Vector<Neuron> getNeurons() {
    return neurons;
  }

  public void setNeurons(Vector<Neuron> neurons) {
    this.neurons = neurons;
  }

}