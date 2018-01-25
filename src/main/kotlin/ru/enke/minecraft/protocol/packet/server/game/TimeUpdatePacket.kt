package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object TimeUpdatePacket : Packet<TimeUpdate> {

    override fun write(message: TimeUpdate, buffer: ByteBuf) {
        buffer.writeLong(message.age)
        buffer.writeLong(message.time)
    }

    override fun read(buffer: ByteBuf): TimeUpdate {
        val age = buffer.readLong()
        val time = buffer.readLong()

        return TimeUpdate(age, time)
    }

}

data class TimeUpdate(val age: Long, val time: Long) : PacketMessage
