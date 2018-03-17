package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.Position

object SignUpdatePacket : Packet<SignUpdate> {

    override fun write(message: SignUpdate, buffer: ByteBuf) {
        buffer.writePosition(message.position)

        for(line in message.lines) {
            buffer.writeString(line!!)
        }
    }

    override fun read(buffer: ByteBuf): SignUpdate {
        val position = buffer.readPosition()
        val lines = arrayOfNulls<String>(4)

        for(i in 0..3) {
           lines[i] = buffer.readString()
        }

        return SignUpdate(position, lines)
    }

}

data class SignUpdate(val position: Position, val lines: Array<String?>) : PacketMessage
