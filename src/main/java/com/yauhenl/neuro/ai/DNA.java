package com.yauhenl.neuro.ai;

import com.yauhenl.neuro.Environment;

import java.util.ArrayList;
import java.util.List;

public class DNA {
    private static final Float MUTATION_RATE = 0.02f;

    private List<Float> weightGenes = new ArrayList<>(32);
    private Float massGene;
    private Environment env;

    public DNA(Environment env) {
        this.env = env;
        for (int i = 0; i < 32; ++i) {
            weightGenes.add(i, env.random(-1, 1));
        }
        massGene = env.random(10, 70);
    }

    private DNA(Environment env, List<Float> weightGenes, Float massGene) {
        this.env = env;
        this.weightGenes = weightGenes;
        this.massGene = massGene;
    }

    public DNA copy() {
        return new DNA(env, new ArrayList<>(weightGenes), massGene);
    }

    public void mutate() {
        for (int i = 0; i < weightGenes.size(); i++) {
            if (env.random(1) < MUTATION_RATE) {
                weightGenes.set(i, env.random(1));
                massGene = env.random(10, 70);
            }
        }
        if (env.random(1) < MUTATION_RATE) {
            massGene = env.random(10, 70);
        }
    }

    List<Float> getWeightGenes() {
        return weightGenes;
    }

    public Float getMassGene() {
        return massGene;
    }
}
