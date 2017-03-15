package com.yauhenl.neuro;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

//The World class manage the entire world
//it create Bloops and Foods and control them
class World {

    private NeuralEvolution neuralEvolution;
    private ArrayList<Bloop> bloops;
    private ArrayList<Food> foods;

    private int bloopsCount = 0;

    World(NeuralEvolution neuralEvolution, int bloopsNum, int foodsNum) {
        this.neuralEvolution = neuralEvolution;
        bloops = new ArrayList<>();
        foods = new ArrayList<>();

        for (int i = 0; i < bloopsNum; ++i) {
            bloops.add(new Bloop(neuralEvolution, bloopsCount++));
        }
        for (int i = 0; i < foodsNum; ++i) {
            foods.add(new Food(neuralEvolution));
        }
    }

    void run() {

        //Update bloops:
        for (int i = 0; i < bloops.size(); ++i) {

            bloops.get(i).update(foods);   //Update Bloop position
            bloops.get(i).eat(foods);  //Bloop eating
            bloops.get(i).display();  //Bloop display


            if (bloops.get(i).health > 800 && neuralEvolution.random(1) < 0.0013f) {  //Bloop reproduction
                bloops.get(i).health -= 100;
                bloops.add(bloops.get(i).reproduce(bloopsCount++));
            }

            if (bloops.get(i).isDead()) {  //Bloop death
                PApplet.println("Bloops n. " + bloops.get(i).id + " is dead.");
                bloops.remove(bloops.get(i));
            }
        }

        //Display Foods:
        for (Food food : foods) {
            food.display();
        }

        //Add some foods:
        if (neuralEvolution.random(1) < 0.05f) {
            foods.add(new Food(neuralEvolution));
        }
    }

    //Used for clicking add:
    void addBloops() {
        float[] goodGenes = {0.65979946f, 0.9237745f, -0.95996153f, 0.984262f, -0.10405171f, 0.8009598f, 0.43076366f, 0.8778855f, 0.042821348f, 0.993147f, 0.57589555f, 0.29872072f, 0.25054073f, 0.050901234f, 0.8436686f, 0.44286066f, -0.23298085f, -0.15841329f, 0.014826775f, -0.6465541f, 0.9070223f, 0.9349151f, -0.13380969f, 0.5622362f, -0.51015556f, 0.7004848f, -0.9447372f, 0.48166156f, 0.9900281f, 0.6025151f, 0.4556185f, -0.2769916f, 52.33656f, 13.038952f, 13.491612f, 40.85528f, 33.24558f, 45.536972f, 11.461625f, 14.195549f};
        DNA dna = new DNA(neuralEvolution, goodGenes);
        bloops.add(new Bloop(neuralEvolution, dna, new PVector(neuralEvolution.mouseX, neuralEvolution.mouseY), bloopsCount++));


        float[] goodGenes2 = {0.20275891f, 0.026649952f, 0.43252873f, -0.40793216f, -0.54590714f, -0.25586605f, 0.3218981f, -0.35193098f, 0.58812934f, 0.83577764f, -0.016791105f, 0.64107144f, 0.97045064f, 0.16259098f, -0.14545023f, 0.7636057f, -0.7766409f, 0.1283921f, 0.16343546f, -0.3811437f, 0.7840737f, -0.7701818f, 0.24260998f, 0.43112117f, 0.90271205f, 0.6994773f, 0.15249717f, 0.75517744f, 0.4281698f, -0.4254781f, 0.6863489f, -0.5756632f, 25.924147f, 6.260845f, 31.409222f, 32.69906f, 13.4582405f, 30.142233f, 47.648323f, 2.0961523f};
        DNA dna2 = new DNA(neuralEvolution, goodGenes2);
        bloops.add(new Bloop(neuralEvolution, dna2, new PVector(neuralEvolution.mouseX + 300, neuralEvolution.mouseY), bloopsCount++));
    }
}
