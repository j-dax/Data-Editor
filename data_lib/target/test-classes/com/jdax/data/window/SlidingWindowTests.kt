package com.jdax.data.window

import com.jdax.data.window.SlidingWindow
import org.testng.annotations.Test
import org.testng.annotations.DataProvider
import org.testng.Assert

@Test
class SlidingWindowTests {

    @Test
    public fun instanceTest() {
        val window: SlidingWindow = SlidingWindow(0, 1, 0, 1, 1)
        Assert.assertEquals(window.width(), 1, "Width should be 1")
        Assert.assertEquals(window.height(), 1, "Height should be 1")
        Assert.assertEquals(window.vertical(), 0..1, "Vertical IntRange should be 0..1")
        Assert.assertEquals(window.horizontal(), 0..1, "Horizontal IntRange should be 0..1")
    }

    @Test
    public fun stepTest() {
        val y1: Int = 200
        val y2: Int = 705
        val stepSize: Int = 20
        val window: SlidingWindow = SlidingWindow(0, 40, y1, y2, stepSize)
        Assert.assertEquals(window.vertical(), y1..y2)
        Assert.assertEquals(window.horizontal(), 0..40)
        Assert.assertEquals(window.getNumVerticalSteps(), 26)
        // begin at y1, containing stepSize elements
        Assert.assertEquals(window.getVerticalStep(0), y1..y1+stepSize-1)
        // get the remainder of elements 700..705
        Assert.assertEquals(window.getVerticalStep(25), 700..y2)
    }

}