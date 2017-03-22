package com.yauhenl.neuro;

import processing.core.PApplet;

import java.util.concurrent.ThreadLocalRandom;

import static java.util.concurrent.ThreadLocalRandom.current;

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

    static Float nextFloat(Integer high) {
        Float value;
        do {
            value = current().nextFloat() * high;
        } while (value.equals((float) high));
        return value;
    }

    static Float nextFloat(Integer low, Integer high) {
        if (low.compareTo(high) >= 0) {
            return (float) low;
        }
        Integer diff = high - low;
        Float value;
        do {
            value = current().nextFloat() * diff + low;
        } while (value.equals((float) high));
        return value;
    }
}
