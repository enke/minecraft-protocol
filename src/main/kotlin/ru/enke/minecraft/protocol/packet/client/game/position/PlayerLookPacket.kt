package ru.enke.minecraft.protocol.packet.client.game.position

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object PlayerLookPacket : Packet<PlayerLook> {

    override fun write(message: PlayerLook, buffer: ByteBuf) {
        buffer.writeFloat(message.yaw)
        buffer.writeFloat(message.pitch)
        buffer.writeBoolean(message.ground)
    }

    override fun read(buffer: ByteBuf): PlayerLook {
        val yaw = buffer.readFloat()
        val pitch = buffer.readFloat()
        val ground = buffer.readBoolean()

        return PlayerLook(yaw, pitch, ground)
    }

}

data class PlayerLook(val yaw: Float, val pitch: Float, val ground: Boolean) : PacketMessage
