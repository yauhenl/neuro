package com.yauhenl.neuro;

import processing.core.PVector;

import static java.util.concurrent.ThreadLocalRandom.current;

//The Food class
class Food {

    private NeuralEvolution neuralEvolution;
    PVector location;

    Food(NeuralEvolution neuralEvolution) {
        this.neuralEvolution = neuralEvolution;
        location = new PVector(current().nextFloat() * neuralEvolution.width, current().nextFloat() * neuralEvolution.height);
    }

    void display() {
        neuralEvolution.noStroke();
        neuralEvolution.fill(200, 50, 0);
        neuralEvolution.rect(location.x, location.y, 7, 7);
    }
}
