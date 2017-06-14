package com.yauhenl.kotlin.neuro

import java.util.ArrayList

class World internal constructor(private val env: Environment, bloopsCount: Int, foodCount: Int) {
    private val bloops = ArrayList<Bloop>()
    private val foods = ArrayList<Food>()

    init {
        for (i in 0..bloopsCount - 1) {
            bloops.add(Bloop(env, i))
        }
        for (i in 0..foodCount - 1) {
            foods.add(Food(env))
        }
    }

    internal fun draw() {
        for (i in bloops.indices) {
            val it = bloops[i]
            it.update(foods, bloops)
            if (it.isDead) {
                bloops.remove(it)
            }
        }
        foods.forEach { it.display() }
        if (env.random(1f) < 0.05f) {
            foods.add(Food(env))
        }
    }
}