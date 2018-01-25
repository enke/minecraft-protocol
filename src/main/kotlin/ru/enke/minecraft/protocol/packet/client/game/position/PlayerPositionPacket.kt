package ru.enke.minecraft.protocol.packet.client.game.position

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object PlayerPositionPacket : Packet<PlayerPosition> {

    override fun write(message: PlayerPosition, buffer: ByteBuf) {
        buffer.writeDouble(message.x)
        buffer.writeDouble(message.y)
        buffer.writeDouble(message.z)
        buffer.writeBoolean(message.ground)
    }

    override fun read(buffer: ByteBuf): PlayerPosition {
        val x = buffer.readDouble()
        val y = buffer.readDouble()
        val z = buffer.readDouble()
        val ground = buffer.readBoolean()

        return PlayerPosition(x, y, z, ground)
    }

}

data class PlayerPosition(val x: Double, val y: Double, val z: Double, val ground: Boolean) : PacketMessage
