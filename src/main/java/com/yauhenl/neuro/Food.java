package com.yauhenl.neuro;

import processing.core.PVector;

class Food {
    private final PVector location;
    private Environment env;

    PVector getLocation() {
        return location;
    }

    Food(Environment environment) {
        env = environment;
        location = new PVector(env.random(env.width), env.random(env.height));
    }

    void display() {
        env.noStroke();
        env.fill(200, 50, 0);
        env.rect(location.x, location.y, 8, 8);
    }
}
