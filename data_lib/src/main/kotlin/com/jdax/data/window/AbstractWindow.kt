package com.jdax.data.window

abstract class AbstractWindow(open val x1: Int, open val x2: Int, open val y1: Int, open val y2: Int) {
    
    public fun width(): Int {
        return x2 - x1
    }

    public fun height(): Int {
        return y2 - y1
    }

    public fun vertical(): IntRange {
        return y1..y2
    }

    public fun horizontal(): IntRange {
        return x1..x2
    }

}