package com.jdax.data.window

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.MapperFeature
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.context.SpringBootTest
import org.testng.annotations.Test
import org.testng.Assert

@JsonTest
@SpringBootTest
class SlidingWindowSerdeTests(val mapper: ObjectMapper = ObjectMapper()) {

    @Test
    public fun testControl() {
        Assert.assertEquals(0..1, 0..1, "control not self-comparable")
    }

    @Test
    public fun testDeserialize() {
        val json: String = "{\"x1\": 3, \"x2\": 5, \"y1\": 2, \"y2\": 4}"
        // val window: SlidingWindow = SlidingWindow(1..2, 1..2)
        // mapper.readValue(json, window.javaClass)
        val window: SlidingWindow = mapper.readValue(json, SlidingWindow(0..1, 0..1).javaClass)
        Assert.assertEquals(window.horizontal(), 3..5, "horizontal does not match")
        Assert.assertEquals(window.vertical(), 2..4, "vertical does not match")
    }

    @Test
    public fun testSerialization() {
        val window: SlidingWindow = SlidingWindow(0..5, 10..15)
        val json: String = mapper.writeValueAsString(window)
        Assert.assertEquals(json, "{\"x1\":0,\"x2\":5,\"y1\":10,\"y2\":15}")
    }

    @Test
    public fun testFailSerialization() {
        val window: SlidingWindow = SlidingWindow(0..0, 0..0)
        val json: String = mapper.writeValueAsString(window)
        Assert.assertNotEquals(json, "{\"x1\":0,\"x2\":5,\"y1\":10,\"y2\":15}")
    }
}