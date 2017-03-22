package com.yauhenl.neuro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static java.util.concurrent.ThreadLocalRandom.current;

//The World class manage the entire world
//it create Bloops and Foods and control them
class World {
    private static final Logger LOGGER = LoggerFactory.getLogger(World.class);

    private NeuralEvolution neuralEvolution;
    private ArrayList<Bloop> bloops;
    private ArrayList<Food> foods;

    private int bloopsCount = 0;

    World(NeuralEvolution neuralEvolution, int bloopsNum, int foodsNum) {
        this.neuralEvolution = neuralEvolution;
        bloops = new ArrayList<>();
        foods = new ArrayList<>();

        for (int i = 0; i < bloopsNum; i++) {
            bloops.add(new Bloop(neuralEvolution, bloopsCount++));
        }
        for (int i = 0; i < foodsNum; i++) {
            foods.add(new Food(neuralEvolution));
        }
    }

    void run() {

        //Update bloops:
        for (int i = 0; i < bloops.size(); i++) {

            bloops.get(i).update(foods);   //Update Bloop position
            bloops.get(i).eat(foods);  //Bloop eating
            bloops.get(i).display();  //Bloop display


            if ((bloops.get(i).health > 800) && (current().nextFloat() < 0.0013f)) {  //Bloop reproduction
                bloops.get(i).health -= 100;
                bloops.add(bloops.get(i).reproduce(bloopsCount++));
            }

            if (bloops.get(i).isDead()) {  //Bloop death
                LOGGER.info("Bloops {} is dead.", bloops.get(i).id);
                bloops.remove(bloops.get(i));
            }
        }

        //Display Foods:
        for (Food food : foods) {
            food.display();
        }

        //Add some foods:
        if (current().nextFloat() < 0.05f) {
            foods.add(new Food(neuralEvolution));
        }
    }
}
