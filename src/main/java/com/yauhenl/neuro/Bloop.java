package com.yauhenl.neuro;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

//The Bloop class
class Bloop {

    private NeuralEvolution neuralEvolution;
    int id;  //Identification number of Bloops
    private float mass;  //Mass of the Bloops, it affect movement and size
    int health;  //Health of the Bloops
    private PVector location;
    private PVector velocity;
    private PVector acceleration;

    private DNA dna;
    private Brain brain;

    //Force that move the Bloops
    private PVector leftForce = new PVector(0.05f, 0);
    private PVector rightForce = new PVector(-0.05f, 0);
    private PVector upForce = new PVector(0, 0.05f);
    private PVector downForce = new PVector(0, -0.05f);

    private float[] input;

    //Constructor with a random DNA, random Location
    Bloop(NeuralEvolution neuralEvolution, int id) {
        this.neuralEvolution = neuralEvolution;

        this.id = id;
        dna = new DNA(neuralEvolution);  //create random DNA
        brain = new Brain(dna);  //create the brain

        mass = dna.genes[32];
        health = 1000;

        location = new PVector(neuralEvolution.random(neuralEvolution.width), neuralEvolution.random(neuralEvolution.height));
        velocity = new PVector(0, 0);
        acceleration = new PVector(0.0f, 0.0f);

        input = new float[4];

        printInformation();
    }

    //Constructor with specific DNA and Location
    Bloop(NeuralEvolution neuralEvolution, DNA dna, PVector loc, int id) {
        this.neuralEvolution = neuralEvolution;

        this.id = id;
        this.dna = dna;
        brain = new Brain(dna);

        mass = dna.genes[32];
        health = 1000;

        location = new PVector(loc.x + 10, loc.y + 10);
        velocity = new PVector(0, 0);
        acceleration = new PVector(0.0f, 0.0f);

        input = new float[4];

        printInformation();
    }

    //Update Bloops location, with Neural Network
    void update(ArrayList<Food> foods) {


        //If there is some foods:
        if (!foods.isEmpty()) {

            //Find the closest foods:
            Food closestFood = foods.get(0);
            float distMin = PVector.dist(closestFood.location, location);
            for (int i = 1; i < foods.size(); ++i) {
                float dist = PVector.dist(foods.get(i).location, location);
                if (dist < distMin) {
                    closestFood = foods.get(i);
                    distMin = dist;
                }
            }

            neuralEvolution.stroke(255, 255, 255, 100);
            neuralEvolution.line(closestFood.location.x, closestFood.location.y, location.x, location.y);

            float distX = location.x - closestFood.location.x;
            float distY = location.y - closestFood.location.y;

            //Create the input for the brain:
            input[0] = distX / 100;
            input[1] = distY / 100;
            input[2] = 0;
            input[3] = 0;

            //Compute the Neural Network Output
            float[] move = brain.computeOutput(input);

            //Apply force:
            if (Float.compare(move[0], 1) == 0) {
                this.applyForce(leftForce);
            }
            if (Float.compare(move[1], 1) == 0) {
                this.applyForce(rightForce);
            }
            if (Float.compare(move[2], 1) == 0) {
                this.applyForce(upForce);
            }
            if (Float.compare(move[3], 1) == 0) {
                this.applyForce(downForce);
            }
        }

        //Move the bloops:
        velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);
        velocity.mult(0.95f); //Add some friction

        //If reach the border:
        if (location.x < 0) {
            location.x = neuralEvolution.width;
        }
        if (location.x > neuralEvolution.width) {
            location.x = 0;
        }
        if (location.y < 0) {
            location.y = neuralEvolution.height;
        }
        if (location.y > neuralEvolution.height) {
            location.y = 0;
        }

        //Decrement health:
        health -= 1;
    }

    //Apply a force:
    private void applyForce(PVector force) {
        PVector f = PVector.div(force, mass / 35);
        acceleration.add(f);
    }

    //Eat the Foods:
    void eat(ArrayList<Food> foods) {

        //Search if there is food:
        for (int i = 0; i < foods.size(); ++i) {

            float d = PVector.dist(location, foods.get(i).location);

            if (d < mass / 2) {
                health += 300;
                foods.remove(foods.get(i));  //Remove foods from array
            }

        }
    }

    //Draw the Bloops:
    void display() {

        neuralEvolution.noStroke();

        if (health > 800) {
            neuralEvolution.stroke(0, 200, 0);
        }

        neuralEvolution.fill(PApplet.map(health, 0, 1000, 33, 255), 200);

        neuralEvolution.ellipse(location.x - 5, location.y, mass, mass);

        neuralEvolution.fill(255);
        neuralEvolution.text(id, location.x, location.y);
    }

    //Reproduction:
    Bloop reproduce(int id) {
        PApplet.println("Reproduction! ");
        DNA childDNA = dna.copy();
        childDNA.mutate(0.02f); //2% mutation rate
        return new Bloop(neuralEvolution, childDNA, location, id);
    }

    //Print Bloops information:
    private void printInformation() {
        PApplet.println("Bloops number: " + id);
        PApplet.println("Bloops mass: " + mass);
        PApplet.print("DNA: ");
        for (int i = 0; i < dna.genes.length; ++i) {
            PApplet.print(dna.genes[i]);
            PApplet.print(" ");
        }
        PApplet.println("    Dna-End");
        PApplet.println("Location: " + location.x + "  " + location.y);
        PApplet.println("Health: " + health);
        PApplet.println("\n");
    }

    //Check if a Bloops is Dead:
    boolean isDead() {
        return health < 0.0f;
    }
}
