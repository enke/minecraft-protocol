package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.Position

object ClientUpdateSignPacket : Packet<ClientUpdateSign> {

    override fun write(message: ClientUpdateSign, buffer: ByteBuf) {
        buffer.writePosition(message.position)

        for(line in message.lines) {
            buffer.writeString(line!!)
        }
    }

    override fun read(buffer: ByteBuf): ClientUpdateSign {
        val position = buffer.readPosition()
        val lines = arrayOfNulls<String>(4)

        for(i in 0..3) {
           lines[i] = buffer.readString()
        }

        return ClientUpdateSign(position, lines)
    }

}

data class ClientUpdateSign(val position: Position, val lines: Array<String?>) : PacketMessage
