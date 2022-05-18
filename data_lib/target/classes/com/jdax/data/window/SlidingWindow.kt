package com.jdax.data.window

import com.jdax.data.window.AbstractWindow

data class SlidingWindow constructor(
    override val x1: Int,
    override val x2: Int,
    override val y1: Int,
    override val y2: Int,
    val stepSize: Int):
    AbstractWindow(x1, x2, y1, y2) {
    
    public fun getNumVerticalSteps(): Int {
        return (y2 - y1) / stepSize + (if (y2 - y1 % stepSize > 0) 1 else 0)
    }

    public fun getVerticalStep(stepNum: Int): IntRange? {
        val frameStart: Int = stepNum * stepSize + y1
        if (frameStart > y2) {
            return null
        }
        val frameEnd: Int = Math.min(frameStart + stepSize - 1, y2)
        return frameStart..frameEnd
    }

}