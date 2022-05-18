package com.jdax.data.window

data class FullWindow constructor(
    override val x1: Int = 0,
    override val x2: Int = 1,
    override val y1: Int = 0,
    override val y2: Int = 1): AbstractWindow(x1, x2, y1, y2) {
    constructor(horizontal: IntRange,
        vertical: IntRange) : this(horizontal.first,
        horizontal.last,
        vertical.first,
        vertical.last)
}
