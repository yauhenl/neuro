package com.yauhenl.neuro.ai;

public class Brain {
    private final Perceptron[][] layers;
    private final Integer layersNum = 2;
    private final Integer perceptronNum = 4;

    public Brain(DNA dna) {
        layers = new Perceptron[layersNum][perceptronNum];
        for (int i = 0; i < layersNum; ++i) {
            for (int j = 0; j < perceptronNum; ++j) {
                float[] w = new float[4];
                w[0] = dna.getWeightGenes().get(16 * i + 4 * j);
                w[1] = dna.getWeightGenes().get(16 * i + 4 * j + 1);
                w[2] = dna.getWeightGenes().get(16 * i + 4 * j + 2);
                w[3] = dna.getWeightGenes().get(16 * i + 4 * j + 3);
                layers[i][j] = new Perceptron(perceptronNum, w);
            }
        }
    }

    public float[] computeOutput(float[] inputs) {
        float[][] out = new float[layersNum + 1][perceptronNum];
        out[0] = inputs;
        for (int i = 0; i < layersNum; ++i) {
            for (int j = 0; j < perceptronNum; ++j) {
                out[i + 1][j] = layers[i][j].compute(out[i]);
                out[i + 1][j] = layers[i][j].compute(out[i]);
                out[i + 1][j] = layers[i][j].compute(out[i]);
                out[i + 1][j] = layers[i][j].compute(out[i]);
            }
        }
        return out[layersNum];
    }
}
