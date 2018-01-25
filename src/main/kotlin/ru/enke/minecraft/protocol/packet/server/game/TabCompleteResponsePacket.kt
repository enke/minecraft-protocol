package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*

object TabCompleteResponsePacket : Packet<TabCompleteResponse> {

    override fun write(message: TabCompleteResponse, buffer: ByteBuf) {
        val matches = message.matches

        buffer.writeVarInt(matches.size)

        for(match in matches) {
            buffer.writeString(match)
        }
    }

    override fun read(buffer: ByteBuf): TabCompleteResponse {
        val matches = mutableListOf<String>()

        for(i in 0..buffer.readVarInt() - 1) {
            matches.add(buffer.readString())
        }

        return TabCompleteResponse(matches)
    }

}

data class TabCompleteResponse(val matches: List<String>) : PacketMessage
