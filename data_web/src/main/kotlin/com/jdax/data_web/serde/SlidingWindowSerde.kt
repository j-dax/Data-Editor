package com.jdax.data_web.serde

import org.springframework.boot.jackson.JsonComponent
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.node.TextNode

@JsonComponent
public class SlidingWindowSerde {
    public class SlidingWindowSerializer : JsonSerializer<FullSlidingWindow>() {

        override public fun serialize(
                window: FullSlidingWindow,
                jsonGenerator: JsonGenerator,
                serializerProvider: SerializerProvider
        ) {
            jsonGenerator.writeStartObject()
            jsonGenerator.writePOJOField("x1", window.x1)
            jsonGenerator.writePOJOField("x2", window.x2)
            jsonGenerator.writePOJOField("y1", window.y1)
            jsonGenerator.writePOJOField("y2", window.y2)
            jsonGenerator.writeEndObject()
        }
    }

    public class SlidingWindowDeserializer : JsonDeserializer<FullSlidingWindow>() {
 
        override public fun deserialize(
            jsonParser: JsonParser, 
          deserializationContext: DeserializationContext) : FullSlidingWindow {
            val treeNode: TreeNode = jsonParser.getCodec().readTree(jsonParser);
            println(treeNode)
            return FullSlidingWindow(0..0, 0..0)
        }
    }
}
