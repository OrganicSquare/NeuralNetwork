
package com.scollay.neuralnetwork;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

/**
 * Combines {@link NeuronLayer}s together to create an ANN with inputs and outputs. The network must be updated with inputs to
 * retrieve new outputs.
 *
 */

public class NeuralNetwork {

  public Vector<NeuronLayer> layers = new Vector<NeuronLayer>();

  public NeuralNetwork(int inputs, int[] hidden, int outputs) {
    this.layers.add(new NeuronLayer(inputs, 0));
    for (int i = 0; i < hidden.length; i++) {
      if (i > 0) {
        this.layers.add(new NeuronLayer(hidden[i], hidden[i - 1]));
      }
      else {
        this.layers.add(new NeuronLayer(hidden[i], inputs));
      }
    }
    if (hidden.length != 0) {
      this.layers.add(new NeuronLayer(outputs, hidden[hidden.length - 1]));
    }
    else {
      this.layers.add(new NeuronLayer(outputs, inputs));
    }
  }

  public NeuralNetwork(NeuralNetwork brain) {
    NeuralNetwork neuralNet = new NeuralNetwork(brain.getNumInputs(), brain.getHiddenLayersArray(), brain.getNumOutputs());
    for (int i = 0; i < brain.getWeights().size(); i++) {
      neuralNet.setWeights(i, brain.getWeights().get(i));
    }
    for (int i = 0; i < neuralNet.getLayers().size(); i++) {
      layers.add(neuralNet.getLayers().get(i));
    }
  }

  public Vector<Double> update(Vector<Double> inputs) {
    Vector<Double> outputs = new Vector<Double>();
    for (int i = 1; i < this.layers.size(); i++) {
      outputs = new Vector<Double>();
      for (int j = 0; j < this.layers.get(i).getNeurons().size(); j++) {
        outputs.add(this.layers.get(i).getNeurons().get(j).getOutput(inputs));
      }
      inputs = outputs;
    }
    return outputs;
  }

  public void draw(Graphics g, int x, int y, int width, int height) {
    int padding = 0;
    double neuronRadius = 2;
    g.setColor(new Color(255, 255, 255));
    g.fillRect(x, y, width, height);
    g.setColor(new Color(0, 0, 0));
    g.drawRect(x, y, width, height);

    double drawWidth = width - (padding * 2) - (neuronRadius * 2);
    double drawHeight = height - (padding * 2) - (neuronRadius * 2);

    for (int i = 0; i < this.layers.size(); i++) {
      for (int j = 0; j < this.layers.get(i).getNeurons().size(); j++) {
        if (i > 0) {
          for (int k = 0; k < this.layers.get(i).getNeurons().get(j).getWeights().size() - 1; k++) {
            double weighting = this.layers.get(i).getNeurons().get(j).getWeights().get(k);
            if (weighting >= 0) {
              g.setColor(new Color(0, 0, 255, (int) (255 * Math.abs(weighting))));
            }
            else {
              g.setColor(new Color(255, 0, 0, (int) (255 * Math.abs(weighting))));
            }
            g.drawLine((int) (x + padding + neuronRadius + ((i + 0.5) * (drawWidth / (this.layers.size())))),
                (int) (y + padding + neuronRadius + ((j + 0.5) * (drawHeight / (this.layers.get(i).getNeurons().size())))),
                (int) (x + padding + neuronRadius + ((i - 0.5) * (drawWidth / (this.layers.size())))),
                (int) (y + padding + neuronRadius + ((k + 0.5) * (drawHeight / (this.layers.get(i - 1).getNeurons().size())))));
          }
        }
      }
    }

    for (int i = 0; i < this.layers.size(); i++) {
      for (int j = 0; j < this.layers.get(i).getNeurons().size(); j++) {

        g.setColor(new Color(230, 230, 230));
        g.fillOval(
            (int) (x + padding + neuronRadius + ((i + 0.5) * (drawWidth / (this.layers.size()))) - neuronRadius), (int) (y + padding
                + neuronRadius + ((j + 0.5) * (drawHeight / (this.layers.get(i).getNeurons().size()))) - neuronRadius),
            (int) (neuronRadius * 2), (int) (neuronRadius * 2));
        g.setColor(new Color(0, 0, 0));
        g.drawOval(
            (int) (x + padding + neuronRadius + ((i + 0.5) * (drawWidth / (this.layers.size()))) - neuronRadius), (int) (y + padding
                + neuronRadius + ((j + 0.5) * (drawHeight / (this.layers.get(i).getNeurons().size()))) - neuronRadius),
            (int) (neuronRadius * 2), (int) (neuronRadius * 2));
      }
    }
  }

  public Vector<Double> getWeights() {
    Vector<Double> netWeights = new Vector<Double>();
    for (int i = 1; i < this.layers.size(); i++) {
      for (int j = 0; j < this.layers.get(i).getNeurons().size(); j++) {
        for (int k = 0; k < this.layers.get(i).getNeurons().get(j).getWeights().size(); k++) {
          netWeights.add(this.layers.get(i).getNeurons().get(j).getWeights().get(k));
        }
      }
    }
    return netWeights;
  }

  public void setWeights(int index, double newWeight) {
    Vector<Double> tempWeights = this.getWeights();
    tempWeights.set(index, newWeight);
    this.setWeights(tempWeights);
  }

  public void setWeights(Vector<Double> weights) {
    int cWeight = 0;

    for (int i = 1; i < this.layers.size(); i++) {
      for (int j = 0; j < this.layers.get(i).getNeurons().size(); j++) {
        for (int k = 0; k < this.layers.get(i).getNeurons().get(j).getWeights().size(); k++) {
          this.layers.get(i).getNeurons().get(j).getWeights().set(k, weights.get(cWeight++));
        }
      }
    }
  }

  public int getNumInputs() {
    return this.layers.get(0).getNeurons().size();
  }

  public int getNumOutputs() {
    return this.layers.get(layers.size() - 1).getNeurons().size();
  }

  public int[] getHiddenLayersArray() {
    int[] hiddenLayers = new int[this.getLayers().size() - 2];
    for (int i = 0; i < hiddenLayers.length; i++) {
      hiddenLayers[i] = this.getLayers().get(i+1).getNeurons().size();
    }
    return hiddenLayers;
  }

  public Vector<NeuronLayer> getLayers() {
    return layers;
  }

  public void setLayers(Vector<NeuronLayer> layers) {
    this.layers = layers;
  }

}
