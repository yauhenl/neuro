package com.yauhenl.neuro;

import processing.core.PApplet;

public class Environment extends PApplet {

    private com.yauhenl.neuro.World world;

    @Override
    public void setup() {
        world = new com.yauhenl.neuro.World(this, 15, 50);
    }

    @Override
    public void settings() {
        size(1000, 600);
    }

    @Override
    public void draw() {
        background(33);
        world.draw();
    }

    public static void main(String[] args) {
        PApplet.main(Environment.class);
    }
}
