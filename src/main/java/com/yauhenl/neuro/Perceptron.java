package com.yauhenl.neuro;

class Perceptron {

    private float[] weights;

    //Constructior with defined Weights
    //n = number of inputs
    Perceptron(int n, float[] w) {
        weights = new float[n];
        System.arraycopy(w, 0, weights, 0, n);
    }

    //Compute the calculus
    float feedforward(float[] inputs) {
        float sum = 0;
        for (int i = 0; i < weights.length; ++i) {
            sum += inputs[i] * weights[i];
        }
        return activate(sum);  //It returns -1 or 1
    }

    private float activate(float sum) {
        return sum > 0 ? 1 : -1;
    }
}
