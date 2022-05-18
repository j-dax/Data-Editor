package com.jdax.data.window

import com.jdax.data.window.DataWindow
import org.testng.annotations.Test
import org.testng.Assert

@Test
class DataWindowTests {

    @Test
    public fun arrayInstanceTest() {
        val array: Array<Array<String>> = arrayOf(arrayOf("0","1"), arrayOf("2","3"), arrayOf("4","5"))
        val window: DataWindow = DataWindow(array)
        Assert.assertEquals(window.content.size, array.size)
        Assert.assertEquals(window.content[0].size, array[0].size)
        Assert.assertEquals(window.content[1].size, array[1].size)
        Assert.assertEquals(window.content[2].size, array[2].size)
    }
    
    @Test
    public fun listInstanceTest() {
        val list: List<List<String>> = listOf(listOf("0","1"), listOf("2","3"), listOf("4","5"))
        val window: DataWindow = DataWindow(list)
        Assert.assertEquals(window.content.size, list.size)
        Assert.assertEquals(window.content[0].size, list[0].size)
        Assert.assertEquals(window.content[1].size, list[1].size)
        Assert.assertEquals(window.content[2].size, list[2].size)
    }

}