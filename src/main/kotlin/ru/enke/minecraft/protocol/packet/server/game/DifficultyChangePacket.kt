package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.game.Difficulty
import ru.enke.minecraft.protocol.packet.readEnum
import ru.enke.minecraft.protocol.packet.writeEnum

object DifficultyChangePacket : Packet<DifficultyChange> {

    override fun write(message: DifficultyChange, buffer: ByteBuf) {
        buffer.writeEnum(message.difficulty)
    }

    override fun read(buffer: ByteBuf): DifficultyChange {
        return DifficultyChange(buffer.readEnum<Difficulty>())
    }

}

data class DifficultyChange(val difficulty: Difficulty) : PacketMessage
