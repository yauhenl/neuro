package com.yauhenl.kotlin.neuro.ai

internal class Perceptron(n: Int, w: FloatArray) {
    private val weights: FloatArray = FloatArray(n)

    init {
        System.arraycopy(w, 0, weights, 0, n)
    }

    fun compute(inputs: FloatArray): Float {
        var sum = 0f
        for (i in weights.indices) {
            sum += inputs[i] * weights[i]
        }
        return activate(sum)
    }

    private fun activate(sum: Float): Float {
        return (if (sum > 0) 1 else -1).toFloat()
    }
}
