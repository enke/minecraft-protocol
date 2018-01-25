package ru.enke.minecraft.protocol.packet.server.status

import com.google.gson.*
import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.message.Message
import ru.enke.minecraft.protocol.packet.data.message.MessageAdapter
import ru.enke.minecraft.protocol.packet.data.status.Player
import ru.enke.minecraft.protocol.packet.data.status.Players
import ru.enke.minecraft.protocol.packet.data.status.ServerStatusInfo
import ru.enke.minecraft.protocol.packet.readString
import ru.enke.minecraft.protocol.packet.writeString
import java.lang.reflect.Type
import java.util.*

object StatusResponsePacket : Packet<StatusResponse> {

    private val gson = GsonBuilder()
            .registerTypeAdapter(Players::class.java, PlayersAdapter)
            .registerTypeAdapter(Message::class.java, MessageAdapter)
            .create()

    object PlayersAdapter : JsonSerializer<Players>, JsonDeserializer<Players> {
        override fun serialize(players: Players, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            val list = players.list
            val json = JsonObject()

            json.add("online", JsonPrimitive(players.online))
            json.add("max", JsonPrimitive(players.max))

            if(list.isNotEmpty()) {
                val sample = JsonArray()

                for((id, name) in list) {
                    val player = JsonObject()

                    player.add("id", JsonPrimitive(id.toString()))
                    player.add("name", JsonPrimitive(name))
                    sample.add(player)
                }

                json.add("sample", sample)
            }

            return json
        }

        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Players {
            val obj = json.asJsonObject
            val list = ArrayList<Player>()
            val online = obj.get("online").asInt
            val max = obj.get("max").asInt

            if(obj.has("sample")) {
                val sample = obj.getAsJsonArray("sample")

                for(playerObj in sample) {
                    val player = playerObj.asJsonObject
                    val id = UUID.fromString(player.get("id").asString)
                    val name = player.get("name").asString

                    list.add(Player(id, name))
                }
            }

           return Players(online, max, list)
        }
    }

    override fun write(message: StatusResponse, buffer: ByteBuf) {
        buffer.writeString(gson.toJson(message.statusInfo))
    }

    override fun read(buffer: ByteBuf): StatusResponse {
        return StatusResponse(gson.fromJson(buffer.readString(), ServerStatusInfo::class.java))
    }

}

data class StatusResponse(val statusInfo: ServerStatusInfo) : PacketMessage
