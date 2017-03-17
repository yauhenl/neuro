package com.yauhenl.neuro;

class Brain {

    private Perceptron[][] layers;  //Perceptron Layers

    private int layersNum = 2;  //Numbers of Layers
    private int perceptronNum = 4;  //Numbers of perceptron for each Layers

    Brain(DNA dna) {
        layers = new Perceptron[layersNum][perceptronNum];


        //Assign the Weights, using the DNA genes, and create the perceptron layers:
        for (int i = 0; i < layersNum; ++i) {
            for (int j = 0; j < perceptronNum; ++j) {

                float[] w = new float[4];
                w[0] = dna.weightGenes.get(16 * i + 4 * j);
                w[1] = dna.weightGenes.get(16 * i + 4 * j + 1);
                w[2] = dna.weightGenes.get(16 * i + 4 * j + 2);
                w[3] = dna.weightGenes.get(16 * i + 4 * j + 3);

                layers[i][j] = new Perceptron(perceptronNum, w);
            }
        }
    }

    //Compute the Neural Network Outputs
    float[] computeOutput(float[] inputs) {

        float[][] out = new float[layersNum + 1][perceptronNum];

        out[0] = inputs;

        for (int i = 0; i < layersNum; ++i) {
            for (int j = 0; j < perceptronNum; ++j) {

                out[i + 1][j] = layers[i][j].feedforward(out[i]);
                out[i + 1][j] = layers[i][j].feedforward(out[i]);
                out[i + 1][j] = layers[i][j].feedforward(out[i]);
                out[i + 1][j] = layers[i][j].feedforward(out[i]);

            }
        }

        return out[layersNum];
    }
}
