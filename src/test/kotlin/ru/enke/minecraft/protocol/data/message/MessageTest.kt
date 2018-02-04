package ru.enke.minecraft.protocol.data.message

import com.google.gson.JsonParser
import org.junit.Assert.*
import org.junit.Test
import ru.enke.minecraft.protocol.packet.data.message.Message
import ru.enke.minecraft.protocol.packet.data.message.MessageColor.DARK_GREEN
import ru.enke.minecraft.protocol.packet.data.message.MessageColor.WHITE

class MessageTest {

    @Test
    fun testFromJson() {
        val message = Message("Hello,", DARK_GREEN)
                .append(Message("people", WHITE, italic = true, underlined = true))
                .append(Message("!", DARK_GREEN, strikethrough = true))

        val fromJson = Message.fromJson(message.toJson())
        val extra = fromJson.extra

        assertEquals(2, extra.size)
        assertEquals("Hello,", fromJson.text)
        assertEquals(DARK_GREEN, fromJson.color)
        assertEquals("people", extra[0].text)
        assertEquals(WHITE, extra[0].color)
        assertTrue(extra[0].italic)
        assertTrue(extra[0].underlined)
        assertEquals("!", extra[1].text)
        assertEquals(DARK_GREEN, extra[1].color)
        assertTrue(extra[1].strikethrough)
    }

    @Test
    fun testFromString() {
        val message = Message.fromJson("Hello people !")
        assertEquals("Hello people !", message.text)
    }

    @Test
    fun testNullExtra() {
        val json = Message("Hello", DARK_GREEN).toJson()

        val jsonParser = JsonParser()
        val jsonObject = jsonParser.parse(json).asJsonObject

        assertFalse(jsonObject.has("extra"))
    }

}
