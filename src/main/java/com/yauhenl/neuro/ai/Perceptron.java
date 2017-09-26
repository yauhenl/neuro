package com.yauhenl.neuro.ai;

class Perceptron {
    private float[] weights;

    Perceptron(int n, float[] w) {
        weights = new float[n];
        System.arraycopy(w, 0, weights, 0, n);
    }

    float compute(float[] inputs) {
        float sum = 0;
        for (int i = 0; i < weights.length; ++i) {
            sum += inputs[i] * weights[i];
        }
        return activate(sum);
    }

    private float activate(float sum) {
        return sum > 0 ? 1 : -1;
    }
}
