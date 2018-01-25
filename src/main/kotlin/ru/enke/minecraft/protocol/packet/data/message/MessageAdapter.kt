package ru.enke.minecraft.protocol.packet.data.message

import com.google.gson.*
import java.lang.reflect.Type

object MessageAdapter : JsonDeserializer<Message>, JsonSerializer<Message> {

    val parser = JsonParser()

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Message {
        return Message.fromJson(json.toString())
    }

    override fun serialize(value: Message, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return parser.parse(value.toJson())
    }

}
