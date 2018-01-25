package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.Position

object ServerUpdateSignPacket : Packet<ServerUpdateSign> {

    override fun write(message: ServerUpdateSign, buffer: ByteBuf) {
        buffer.writePosition(message.position)

        for(line in message.lines) {
            buffer.writeString(line!!)
        }
    }

    override fun read(buffer: ByteBuf): ServerUpdateSign {
        val position = buffer.readPosition()
        val lines = arrayOfNulls<String>(4)

        for(i in 0..3) {
           lines[i] = buffer.readString()
        }

        return ServerUpdateSign(position, lines)
    }

}

data class ServerUpdateSign(val position: Position, val lines: Array<String?>) : PacketMessage
