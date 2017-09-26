package com.yauhenl.neuro;

import com.yauhenl.neuro.ai.Brain;
import com.yauhenl.neuro.ai.DNA;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.List;

class Bloop {
    private static final PVector leftForce = new PVector(0.05f, 0);
    private static final PVector rightForce = new PVector(-0.05f, 0);
    private static final PVector upForce = new PVector(0, 0.05f);
    private static final PVector downForce = new PVector(0, -0.05f);

    private final Integer id;
    private Integer health = 1000;
    private final Float mass;
    private PVector location;
    private PVector velocity = new PVector(0, 0);
    private PVector acceleration = new PVector(0.0f, 0.0f);
    private final DNA dna;
    private final Brain brain;
    private final float[] input = new float[4];

    private Environment env;

    Bloop(Environment environment, Integer id) {
        this.id = id;
        env = environment;
        dna = new DNA(env);
        brain = new Brain(dna);
        mass = dna.getMassGene();

        location = new PVector(env.random(env.width), env.random(env.height));
    }

    private Bloop(Environment environment, DNA dna, PVector location, int id) {
        env = environment;
        this.id = id;
        this.dna = dna;
        brain = new Brain(dna);
        mass = dna.getMassGene();
        this.location = new PVector(location.x, location.y);
    }

    void update(List<Food> foods, List<Bloop> bloops) {
        if (!foods.isEmpty()) {
            PVector closestFood = getClosestFood(foods).getLocation();
            lineToClosestFood(closestFood);

            float distX = location.x - closestFood.x;
            float distY = location.y - closestFood.y;

            input[0] = distX / 100;
            input[1] = distY / 100;
            input[2] = 0;
            input[3] = 0;

            move(brain.computeOutput(input));

            reachBorder();
            eat(foods);
            if ((health > 800) && (env.random(1) < 0.0013f)) {
                health -= 100;
                bloops.add(reproduce(bloops.size() + 1));
            }
            display();
            health -= 1;
        }
    }

    private void lineToClosestFood(PVector closestFood) {
        env.stroke(255, 255, 255, 100);
        env.line(closestFood.x + 4, closestFood.y + 4, location.x, location.y);
    }

    private void display() {
        env.noStroke();
        if (health > 800) {
            env.stroke(0, 200, 0);
        }

        env.fill(PApplet.map(health, 0, 1000, 33, 255), 200);
        env.ellipse(location.x, location.y, mass, mass);
        env.fill(255);
        env.text(id, location.x, location.y);
    }

    private Bloop reproduce(int id) {
        DNA childDNA = dna.copy();
        childDNA.mutate();
        return new Bloop(env, childDNA, location, id);
    }

    private void reachBorder() {
        if (location.x < 0) {
            location.x = env.width;
        }
        if (location.x > env.width) {
            location.x = 0;
        }
        if (location.y < 0) {
            location.y = env.height;
        }
        if (location.y > env.height) {
            location.y = 0;
        }
    }

    private Food getClosestFood(List<Food> foods) {
        Food closestFood = foods.get(0);
        float distMin = PVector.dist(closestFood.getLocation(), location);
        for (int i = 1; i < foods.size(); ++i) {
            float dist = PVector.dist(foods.get(i).getLocation(), location);
            if (dist < distMin) {
                closestFood = foods.get(i);
                distMin = dist;
            }
        }
        return closestFood;
    }

    private void eat(List<Food> foods) {
        for (int i = 0; i < foods.size(); i++) {
            Food it = foods.get(i);
            float dist = PVector.dist(location, it.getLocation());
            if (dist < mass / 2) {
                health += 300;
                foods.remove(it);
            }
        }
    }

    private void applyForce(PVector force) {
        PVector f = PVector.div(force, mass / 35);
        acceleration.add(f);
    }

    private void move(float[] move) {
        if (Float.compare(move[0], 1) == 0) {
            applyForce(leftForce);
        }
        if (Float.compare(move[1], 1) == 0) {
            applyForce(rightForce);
        }
        if (Float.compare(move[2], 1) == 0) {
            applyForce(upForce);
        }
        if (Float.compare(move[3], 1) == 0) {
            applyForce(downForce);
        }

        velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);
        velocity.mult(0.95f);
    }

    boolean isDead() {
        return health < 1;
    }
}
