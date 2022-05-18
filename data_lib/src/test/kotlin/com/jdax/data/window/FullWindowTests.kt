package com.jdax.data.window

import com.jdax.data.window.FullWindow
import org.testng.annotations.Test
import org.testng.annotations.DataProvider
import org.testng.Assert

@Test
class FullWindowTests {

    @Test
    public fun instanceTest() {
        val window: FullWindow = FullWindow(0..1, 0..1)
        Assert.assertEquals(window.width(), 1, "Width should be 1")
        Assert.assertEquals(window.height(), 1, "Height should be 1")
        Assert.assertEquals(window.vertical(), 0..1, "Vertical IntRange should be 0..1")
        Assert.assertEquals(window.horizontal(), 0..1, "Horizontal IntRange should be 0..1")
    }

}