package com.yauhenl.kotlin.neuro.ai

class Brain(dna: DNA) {
    private val layers: Array<Array<Perceptron?>>
    private val layersNum = 2
    private val perceptronNum = 4

    init {
        layers = Array(layersNum, { arrayOfNulls<Perceptron>(perceptronNum)})
        for (i in 0..layersNum - 1) {
            for (j in 0..perceptronNum - 1) {
                val w = FloatArray(4)
                w[0] = dna.weightGenes[16 * i + 4 * j]
                w[1] = dna.weightGenes[16 * i + 4 * j + 1]
                w[2] = dna.weightGenes[16 * i + 4 * j + 2]
                w[3] = dna.weightGenes[16 * i + 4 * j + 3]
                layers[i][j] = Perceptron(perceptronNum, w)
            }
        }
    }

    fun computeOutput(inputs: FloatArray): FloatArray {
        val out = Array(layersNum + 1) { FloatArray(perceptronNum) }
        out[0] = inputs
        for (i in 0..layersNum - 1) {
            for (j in 0..perceptronNum - 1) {
                out[i + 1][j] = layers[i][j]!!.compute(out[i])
                out[i + 1][j] = layers[i][j]!!.compute(out[i])
                out[i + 1][j] = layers[i][j]!!.compute(out[i])
                out[i + 1][j] = layers[i][j]!!.compute(out[i])
            }
        }
        return out[layersNum]
    }
}
