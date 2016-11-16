package com.scollay.neuralnetwork;

import java.util.Random;
import java.util.Vector;

public class GenePool {

  private Vector<Organism> organisms = new Vector<Organism>();

  public GenePool(Vector<Organism> organisms) {
    this.organisms = organisms;
  }

  public void update() {
    Vector<Organism> newOrganisms = new Vector<Organism>();

    this.sort();
    for (int i = 0; i < 1; i++) {
      Vector<Organism> clones = organisms.get(organisms.size() - 1 - i).makeClones(2);
      if (clones != null) {
        for (int j = 0; j < clones.size(); j++) {
          newOrganisms.add(clones.get(j));
        }
      }
    }

    while (newOrganisms.size() < organisms.size()) {
      Organism parent1 = this.organismRoulette();
      Organism parent2 = this.organismRoulette();
      

      Organism child1 = Organism.breed(parent1, parent2);
      Organism child2 = Organism.breed(parent2, parent1);
      newOrganisms.add(child1);
      newOrganisms.add(child2);

    }

    organisms = newOrganisms;
  }

  public void quickUpdate(int time) {
    for(int i=0;i<time;i++){
      for (int j = 0; j < organisms.size(); j++) {
        organisms.get(j).update();
      }
    }
    this.update();
  }
  
  public void timeLapse(int time){
    for(int i=0;i<time;i++){
      for (int j = 0; j < organisms.size(); j++) {
        organisms.get(j).update();
      }
    }
  }

  public void sort() {
    for (int i = 1; i < organisms.size(); i++) {
      for (int j = i; j > 0; j--) {
        if (organisms.get(j).getFitness() < organisms.get(j - 1).getFitness()) {
          Organism tempOrganism = organisms.get(j);
          organisms.set(j, organisms.get(j - 1));
          organisms.set(j - 1, tempOrganism);

        }
      }
    }
  }

  public Organism organismRoulette() {
    double fitnessThreshold = new Random().nextDouble() * this.getTotalFitness();
    for (int i = 0; i < organisms.size(); i++) {
      fitnessThreshold -= organisms.get(i).getFitness();
      if (fitnessThreshold <= 0) {
        return organisms.get(i).subClass(organisms.get(i).getBrain());
      }
    }
    return organisms.get(0).subClass(organisms.get(0).getBrain());
  }

  public double getAverageFitness() {
    return this.getTotalFitness() / organisms.size();
  }

  public double getTotalFitness() {
    double totalFitness = 0;
    for (int i = 0; i < organisms.size(); i++) {
      totalFitness += organisms.get(i).getFitness();
    }
    return totalFitness;
  }

  public double getWorstFitness() {
    double worstFitness = Math.pow(2, 64);
    for (int i = 0; i < organisms.size(); i++) {
      if (organisms.get(i).getFitness() < worstFitness) {
        worstFitness = organisms.get(i).getFitness();
      }
    }
    return worstFitness;
  }

  public double getBestFitness() {
    double bestFitness = 0;
    for (int i = 0; i < organisms.size(); i++) {
      if (organisms.get(i).getFitness() > bestFitness) {
        bestFitness = organisms.get(i).getFitness();
      }
    }
    return bestFitness;
  }

  
  public Vector<Organism> getOrganisms() {
    return organisms;
  }

  
  public void setOrganisms(Vector<Organism> organisms) {
    this.organisms = organisms;
  }
  

}
