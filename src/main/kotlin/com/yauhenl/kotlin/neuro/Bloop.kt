package com.yauhenl.kotlin.neuro

import com.yauhenl.kotlin.neuro.ai.Brain
import com.yauhenl.kotlin.neuro.ai.DNA
import processing.core.PApplet
import processing.core.PVector
import java.util.ArrayList

class Bloop {

    private val id: Int
    private var health: Int = 1000
    private val mass: Float
    private var location: PVector
    private val velocity = PVector(0f, 0f)
    private val acceleration = PVector(0.0f, 0.0f)
    private val dna: DNA
    private val brain: Brain
    private val input = FloatArray(4)

    private var env: Environment

    internal constructor(environment: Environment, id: Int) {
        this.id = id
        env = environment
        dna = DNA(env)
        brain = Brain(dna)
        mass = dna.massGene

        location = PVector(env.random(env.width.toFloat()), env.random(env.height.toFloat()))
    }

    private constructor(environment: Environment, dna: DNA, location: PVector, id: Int) {
        env = environment
        this.id = id
        this.dna = dna
        brain = Brain(dna)
        mass = dna.massGene
        this.location = PVector(location.x, location.y)
    }

    internal fun update(foods: ArrayList<Food>, bloops: ArrayList<Bloop>) {
        if (!foods.isEmpty()) {
            val closestFood = getClosestFood(foods).location
            lineToClosestFood(closestFood)

            val distX = location.x - closestFood.x
            val distY = location.y - closestFood.y

            input[0] = distX / 100
            input[1] = distY / 100
            input[2] = 0f
            input[3] = 0f

            move(brain.computeOutput(input))

            reachBorder()
            eat(foods)
            if (health > 800 && env.random(1f) < 0.0013f) {
                health -= 100
                bloops.add(reproduce(bloops.size + 1))
            }
            display()
            health -= 1
        }
    }

    private fun lineToClosestFood(closestFood: PVector) {
        env.stroke(255f, 255f, 255f, 100f)
        env.line(closestFood.x + 4, closestFood.y + 4, location.x, location.y)
    }

    private fun display() {
        env.noStroke()
        if (health > 800) {
            env.stroke(0f, 200f, 0f)
        }

        env.fill(PApplet.map(health.toFloat(), 0f, 1000f, 33f, 255f), 200f)
        env.ellipse(location.x, location.y, mass, mass)
        env.fill(255)
        env.text(id, location.x, location.y)
    }

    private fun reproduce(id: Int): Bloop {
        val childDNA = dna.copy()
        childDNA.mutate()
        return Bloop(env, childDNA, location, id)
    }

    private fun reachBorder() {
        if (location.x < 0) {
            location.x = env.width.toFloat()
        }
        if (location.x > env.width) {
            location.x = 0f
        }
        if (location.y < 0) {
            location.y = env.height.toFloat()
        }
        if (location.y > env.height) {
            location.y = 0f
        }
    }

    private fun getClosestFood(foods: List<Food>): Food {
        var closestFood = foods[0]
        var distMin = PVector.dist(closestFood.location, location)
        for (i in 1..foods.size - 1) {
            val dist = PVector.dist(foods[i].location, location)
            if (dist < distMin) {
                closestFood = foods[i]
                distMin = dist
            }
        }
        return closestFood
    }

    private fun eat(foods: ArrayList<Food>) {
        for (i in foods.indices) {
            val it = foods[i]
            val dist = PVector.dist(location, it.location)
            if (dist < mass / 2) {
                health += 300
                foods.remove(it)
            }
        }
    }

    private fun applyForce(force: PVector) {
        val f = PVector.div(force, mass / 35)
        acceleration.add(f)
    }

    private fun move(move: FloatArray) {
        if (java.lang.Float.compare(move[0], 1f) == 0) {
            applyForce(leftForce)
        }
        if (java.lang.Float.compare(move[1], 1f) == 0) {
            applyForce(rightForce)
        }
        if (java.lang.Float.compare(move[2], 1f) == 0) {
            applyForce(upForce)
        }
        if (java.lang.Float.compare(move[3], 1f) == 0) {
            applyForce(downForce)
        }

        velocity.add(acceleration)
        location.add(velocity)
        acceleration.mult(0f)
        velocity.mult(0.95f)
    }

    internal val isDead: Boolean
        get() = health < 1

    companion object {
        private val leftForce = PVector(0.05f, 0f)
        private val rightForce = PVector(-0.05f, 0f)
        private val upForce = PVector(0f, 0.05f)
        private val downForce = PVector(0f, -0.05f)
    }
}
