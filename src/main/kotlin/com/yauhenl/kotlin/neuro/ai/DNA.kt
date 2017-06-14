package com.yauhenl.kotlin.neuro.ai

import com.yauhenl.kotlin.neuro.Environment

import java.util.ArrayList

class DNA {
    private val MUTATION_RATE = 0.02f
    var weightGenes = ArrayList<Float>(32)
    var massGene: Float
    private var env: Environment

    constructor(env: Environment) {
        this.env = env
        for (i in 0..31) {
            weightGenes.add(i, env.random(-1f, 1f))
        }
        massGene = env.random(10f, 70f)
    }

    private constructor(env: Environment, weightGenes: ArrayList<Float>, massGene: Float) {
        this.env = env
        this.weightGenes = weightGenes
        this.massGene = massGene
    }

    fun copy(): DNA {
        return DNA(env, ArrayList(weightGenes), massGene)
    }

    fun mutate() {
        for (i in weightGenes.indices) {
            if (env.random(1f) < MUTATION_RATE) {
                weightGenes[i] = env.random(1f)
                massGene = env.random(10f, 70f)
            }
        }
        if (env.random(1f) < MUTATION_RATE) {
            massGene = env.random(10f, 70f)
        }
    }
}
