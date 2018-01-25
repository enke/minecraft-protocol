package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.Statistic

object StatisticsPacket : Packet<Statistics> {

    override fun write(message: Statistics, buffer: ByteBuf) {
        val list = message.list

        buffer.writeVarInt(list.size)

        for((name, value) in list) {
            buffer.writeString(name)
            buffer.writeVarInt(value)
        }
    }

    override fun read(buffer: ByteBuf): Statistics {
        val list = mutableListOf<Statistic>()

        for(i in 0..buffer.readVarInt() - 1) {
            val name = buffer.readString()
            val value = buffer.readVarInt()

            list.add(Statistic(name, value))
        }

        return Statistics(list)
    }

}

data class Statistics(val list: List<Statistic>) : PacketMessage
