package com.yauhenl.neuro;

import java.util.ArrayList;
import java.util.List;


class World {
    private final List<Bloop> bloops = new ArrayList<>();
    private final List<Food> foods = new ArrayList<>();

    private Environment env;

    World(Environment environment, Integer bloopsCount, Integer foodCount) {
        env = environment;
        for (int i = 0; i < bloopsCount; i++) {
            bloops.add(new Bloop(env, i));
        }
        for (int i = 0; i < foodCount; i++) {
            foods.add(new Food(env));
        }
    }

    void draw() {
        for (int i = 0; i < bloops.size(); i++) {
            Bloop it = bloops.get(i);
            it.update(foods, bloops);
            if (it.isDead()) {
                bloops.remove(it);
            }
        }
        foods.forEach(Food::display);
        if (env.random(1) < 0.05f) {
            foods.add(new Food(env));
        }
    }
}
