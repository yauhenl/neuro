package com.yauhenl.kotlin.neuro

import com.yauhenl.kotlin.neuro.Environment
import processing.core.PVector

class Food internal constructor(private val env: Environment) {
    val location: PVector = PVector(env.random(env.width.toFloat()), env.random(env.height.toFloat()))

    fun display() {
        env.noStroke()
        env.fill(200f, 50f, 0f)
        env.rect(location.x, location.y, 8f, 8f)
    }
}
