package com.yauhenl.neuro;

import processing.core.PApplet;

public class NeuralEvolution extends PApplet {

    private World myworld;

    @Override
    public void setup() {
        //Initialize my world:
        myworld = new World(this, 15, 50);
    }

    @Override
    public void draw() {
        background(33);

        //Run my world
        myworld.run();
    }

    @Override
    public void settings() {
        size(1000, 600);
    }

    public static void main(String[] passedArgs) {
        PApplet.main(NeuralEvolution.class);
    }
}
