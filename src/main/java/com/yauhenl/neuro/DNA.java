package com.yauhenl.neuro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static processing.core.PApplet.arrayCopy;

class DNA {
    private static final Logger LOGGER = LoggerFactory.getLogger(DNA.class);

    //The genes include: Neural Network Weights, Mass
    float[] genes;

    private NeuralEvolution neuralEvolution;

    //Construction, with random genes
    DNA(NeuralEvolution neuralEvolution) {
        this.neuralEvolution = neuralEvolution;

        genes = new float[40];
        for (int i = 0; i < 32; ++i) {  //Weights genes
            genes[i] = neuralEvolution.random(-1, 1);
        }
        for (int i = 32; i < 33; ++i) {  //Mass Gene
            genes[i] = neuralEvolution.random(10, 70);
        }
        for (int i = 33; i < 40; ++i) {  //Others Genes - not used
            genes[i] = neuralEvolution.random(0, 50);
        }
    }

    //Construction, with defined genes
    private DNA(NeuralEvolution neuralEvolution, float[] f) {
        this.neuralEvolution = neuralEvolution;
        genes = f;
    }

    //Return the exact copy of the dna
    DNA copy() {
        float[] newgenes = new float[genes.length];
        arrayCopy(genes, newgenes);
        return new DNA(neuralEvolution, newgenes);
    }

    //Mutate the genes
    void mutate(float mutationRate) {

        for (int i = 0; i < 32; i++) {
            if (neuralEvolution.random(1) < mutationRate) {
                LOGGER.info("Mutation!");
                genes[i] = neuralEvolution.random(0, 1);
            }
        }
        for (int i = 32; i < 33; i++) {
            if (neuralEvolution.random(1) < mutationRate) {
                LOGGER.info("Mutation!");
                genes[i] = neuralEvolution.random(50, 150);
            }
        }
        for (int i = 33; i < 40; i++) {
            if (neuralEvolution.random(1) < mutationRate) {
                LOGGER.info("Mutation!");
                genes[i] = neuralEvolution.random(10, 50);
            }
        }
    }
}
