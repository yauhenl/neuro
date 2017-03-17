package com.yauhenl.neuro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class DNA {
    private static final Logger LOGGER = LoggerFactory.getLogger(DNA.class);
    private static final String MUTATION = "Mutation!";
    private static final Float MUTATION_RATE = 0.02f;

    //The genes include: Neural Network Weights, Mass
    List<Float> weightGenes = new ArrayList<>();
    Float massGene;

    private NeuralEvolution neuralEvolution;

    //Construction, with random genes
    DNA(NeuralEvolution neuralEvolution) {
        this.neuralEvolution = neuralEvolution;

        for (int i = 0; i < 32; ++i) {  //Weights genes
            weightGenes.add(i, neuralEvolution.random(-1, 1));
        }
        massGene = neuralEvolution.random(10, 70);
    }

    //Construction, with defined genes
    private DNA(NeuralEvolution neuralEvolution, List<Float> weightGenes, Float massGene) {
        this.neuralEvolution = neuralEvolution;
        this.weightGenes = weightGenes;
        this.massGene = massGene;
    }

    //Return the exact copy of the dna
    DNA copy() {
        Float newMassGene = massGene;
        List<Float> newWeightGenes = new ArrayList<>(weightGenes);
        return new DNA(neuralEvolution, newWeightGenes, newMassGene);
    }

    //Mutate the genes
    void mutate() {
        for (int i = 0; i < weightGenes.size(); i++) {
            if (neuralEvolution.random(1) < MUTATION_RATE) {
                LOGGER.info(MUTATION);
                weightGenes.set(i, neuralEvolution.random(0, 1));
                massGene = neuralEvolution.random(10, 70);
            }
        }
        if (neuralEvolution.random(1) < MUTATION_RATE) {
            LOGGER.info(MUTATION);
            massGene = neuralEvolution.random(10, 70);
        }
    }
}
