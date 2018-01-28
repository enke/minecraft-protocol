package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*

object PlaySoundPacket : Packet<PlaySound> {

    override fun write(message: PlaySound, buffer: ByteBuf) {
        buffer.writeString(message.name)
        buffer.writeVarInt(message.category)
        buffer.writeInt((message.x * 8).toInt())
        buffer.writeInt((message.y * 8).toInt())
        buffer.writeInt((message.z * 8).toInt())
        buffer.writeFloat(message.volume)
        buffer.writeFloat(message.pitch)
    }

    override fun read(buffer: ByteBuf): PlaySound {
        val name = buffer.readString()
        val category = buffer.readVarInt()
        val x = buffer.readInt() / 8.0
        val y = buffer.readInt() / 8.0
        val z = buffer.readInt() / 8.0
        val volume = buffer.readFloat()
        val pitch = buffer.readFloat()

        return PlaySound(name, category, x, y, z, volume, pitch)
    }

}

data class PlaySound(val name: String,
                     val category: Int,
                     val x: Double,
                     val y: Double,
                     val z: Double,
                     val volume: Float,
                     val pitch: Float) : PacketMessage
