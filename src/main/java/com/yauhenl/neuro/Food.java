package com.yauhenl.neuro;

import processing.core.PVector;

//The Food class
class Food {

    private NeuralEvolution neuralEvolution;
    PVector location;

    Food(NeuralEvolution neuralEvolution) {
        this.neuralEvolution = neuralEvolution;
        location = new PVector(neuralEvolution.random(neuralEvolution.width), neuralEvolution.random(neuralEvolution.height));
    }

    void display() {
        neuralEvolution.noStroke();
        neuralEvolution.fill(200, 50, 0);
        neuralEvolution.rect(location.x, location.y, 7, 7);
    }
}
