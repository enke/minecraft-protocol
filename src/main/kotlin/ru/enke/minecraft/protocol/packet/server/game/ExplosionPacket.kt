package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.game.Position

object ExplosionPacket : Packet<Explosion> {

    override fun write(message: Explosion, buffer: ByteBuf) {
        buffer.writeFloat(message.x)
        buffer.writeFloat(message.y)
        buffer.writeFloat(message.z)
        buffer.writeFloat(message.radius)
        buffer.writeInt(message.records.size)

        for((x, y, z) in message.records) {
            buffer.writeByte(x)
            buffer.writeByte(y)
            buffer.writeByte(z)
        }

        buffer.writeFloat(message.velocityX)
        buffer.writeFloat(message.velocityY)
        buffer.writeFloat(message.velocityZ)
    }

    override fun read(buffer: ByteBuf): Explosion {
        val x = buffer.readFloat()
        val y = buffer.readFloat()
        val z = buffer.readFloat()
        val radius = buffer.readFloat()
        val length = buffer.readInt()
        val records = ArrayList<Position>(length)

        for(i in 1..length) {
            val recordX = buffer.readByte().toInt()
            val recordY = buffer.readByte().toInt()
            val recordZ = buffer.readByte().toInt()

            records.add(Position(recordX, recordY, recordZ))
        }

        val velocityX = buffer.readFloat()
        val velocityY = buffer.readFloat()
        val velocityZ = buffer.readFloat()

        return Explosion(x, y, z, radius, records, velocityX, velocityY, velocityZ)
    }

}

data class Explosion(val x: Float,
                     val y: Float,
                     val z: Float,
                     val radius: Float,
                     val records: List<Position>,
                     val velocityX: Float,
                     val velocityY: Float,
                     val velocityZ: Float) : PacketMessage
