package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.game.Difficulty
import ru.enke.minecraft.protocol.packet.data.game.GameMode
import ru.enke.minecraft.protocol.packet.data.game.WorldType
import ru.enke.minecraft.protocol.packet.readEnum
import ru.enke.minecraft.protocol.packet.writeEnum

object RespawnPacket : Packet<Respawn> {

    override fun write(message: Respawn, buffer: ByteBuf) {
        buffer.writeInt(message.dimension)
        buffer.writeEnum(message.difficulty)
        buffer.writeEnum(message.gameMode)
        buffer.writeEnum(message.worldType)
    }

    override fun read(buffer: ByteBuf): Respawn {
        val dimension = buffer.readInt()
        val difficulty = buffer.readEnum<Difficulty>()
        val gameMode = buffer.readEnum<GameMode>()
        val worldType = buffer.readEnum<WorldType>()

        return Respawn(dimension, difficulty, gameMode, worldType)
    }

}

data class Respawn(val dimension: Int, val difficulty: Difficulty, val gameMode: GameMode,
                   val worldType: WorldType) : PacketMessage
