package ru.enke.minecraft.protocol.packet.data.message

import com.google.gson.*
import java.lang.reflect.Type

data class Message @JvmOverloads constructor(var text: String?,
                   var color: MessageColor = MessageColor.WHITE,
                   var bold: Boolean = false,
                   var italic: Boolean = false,
                   var underlined: Boolean = false,
                   var strikethrough: Boolean = false,
                   var obfuscated: Boolean = false,
                   var insertion: String? = null,
                   var hoverEvent: HoverEvent? = null,
                   var clickEvent: ClickEvent? = null,
                   val extra: MutableList<Message> = mutableListOf<Message>()) {

    companion object {
        private val gson = GsonBuilder()
                .registerTypeAdapter(MessageColor::class.java, EnumAdapter<MessageColor>())
                .registerTypeAdapter(HoverAction::class.java, EnumAdapter<HoverAction>())
                .registerTypeAdapter(ClickAction::class.java, EnumAdapter<ClickAction>())
                .create()

        fun fromJson(json: String): Message {
            // It is assumed that we did not receive JSON, but plain text.
            if(!json.startsWith("[") && !json.startsWith("{")) {
                return Message(json)
            }

            return gson.fromJson(json, Message::class.java)
        }
    }

    private class EnumAdapter<T : Enum<T>> : JsonDeserializer<Enum<T>>, JsonSerializer<Enum<T>> {
        @Suppress("UNCHECKED_CAST")
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Enum<T> {
            return java.lang.Enum.valueOf(Class.forName(typeOfT.typeName) as Class<T>, json.asString.toUpperCase())
        }

        override fun serialize(value: Enum<T>, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(value.name.toLowerCase())
        }
    }

    fun append(text: String): Message {
        return append(Message(text))
    }

    fun append(message: Message): Message {
        extra.add(message)
        return this
    }

    fun toJson(): String {
        return gson.toJson(this)
    }

}
