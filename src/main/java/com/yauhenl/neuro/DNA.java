package com.yauhenl.neuro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.yauhenl.neuro.NeuralEvolution.nextFloat;
import static java.util.concurrent.ThreadLocalRandom.current;

class DNA {
    private static final Logger LOGGER = LoggerFactory.getLogger(DNA.class);
    private static final String MUTATION = "Mutation!";
    private static final Float MUTATION_RATE = 0.02f;

    //The genes include: Neural Network Weights, Mass
    List<Float> weightGenes = new ArrayList<>();
    Float massGene;

    //Construction, with random genes
    DNA() {
        for (int i = 0; i < 32; ++i) {  //Weights genes
            weightGenes.add(i, nextFloat(-1, 1));
        }
        massGene = nextFloat(10, 70);
    }

    //Construction, with defined genes
    private DNA(List<Float> weightGenes, Float massGene) {
        this.weightGenes = weightGenes;
        this.massGene = massGene;
    }

    //Return the exact copy of the dna
    DNA copy() {
        Float newMassGene = massGene;
        List<Float> newWeightGenes = new ArrayList<>(weightGenes);
        return new DNA(newWeightGenes, newMassGene);
    }

    //Mutate the genes
    void mutate() {
        for (int i = 0; i < weightGenes.size(); i++) {
            if (current().nextFloat() < MUTATION_RATE) {
                LOGGER.info(MUTATION);
                weightGenes.set(i, nextFloat(0, 1));
                massGene = nextFloat(10, 70);
            }
        }
        if (current().nextFloat() < MUTATION_RATE) {
            LOGGER.info(MUTATION);
            massGene = nextFloat(10, 70);
        }
    }
}
