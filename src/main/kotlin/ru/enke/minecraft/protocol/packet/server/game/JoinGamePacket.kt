package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.Difficulty
import ru.enke.minecraft.protocol.packet.data.game.GameMode
import ru.enke.minecraft.protocol.packet.data.game.WorldType

object JoinGamePacket : Packet<JoinGame> {

    override fun write(message: JoinGame, buffer: ByteBuf) {
        buffer.writeInt(message.entityId)
        buffer.writeEnum(message.gameMode)
        buffer.writeByte(message.dimension)
        buffer.writeEnum(message.difficulty)
        buffer.writeByte(message.maxPlayers)
        buffer.writeEnumAsString(message.worldType)
        buffer.writeBoolean(message.reducedDebugInfo)
    }

    override fun read(buffer: ByteBuf): JoinGame {
        val entityId = buffer.readInt()
        val gameMode = buffer.readEnum<GameMode>()
        val dimension = buffer.readByte().toInt()
        val difficulty = buffer.readEnum<Difficulty>()
        val maxPlayers = buffer.readByte().toInt()
        val worldType = buffer.readEnumAsString<WorldType>()
        val reducedDebugInfo = buffer.readBoolean()

        return JoinGame(entityId, gameMode, dimension, difficulty, maxPlayers, worldType, reducedDebugInfo)
    }

}

data class JoinGame(val entityId: Int, val gameMode: GameMode, val dimension: Int, val difficulty: Difficulty,
                    val maxPlayers: Int, val worldType: WorldType, val reducedDebugInfo: Boolean = true) : PacketMessage
