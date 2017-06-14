package com.yauhenl.kotlin.neuro

import processing.core.PApplet

class Environment : PApplet() {

    private var world: World? = null

    override fun setup() {
        world = World(this, 15, 50)
    }

    override fun settings() {
        size(1000, 600)
    }

    override fun draw() {
        background(33)
        world?.draw()
    }
}

fun main(args: Array<String>) {
    PApplet.main(Environment::class.java)
}