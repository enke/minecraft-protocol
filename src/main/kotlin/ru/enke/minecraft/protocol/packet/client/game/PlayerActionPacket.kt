package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.PlayerActionType

object PlayerActionPacket : Packet<PlayerAction> {

    override fun write(message: PlayerAction, buffer: ByteBuf) {
        buffer.writeVarInt(message.entityId)
        buffer.writeEnum(message.type)
        buffer.writeVarInt(message.jumpBoost)
    }

    override fun read(buffer: ByteBuf): PlayerAction {
        val entityId = buffer.readVarInt()
        val type = buffer.readEnum<PlayerActionType>()
        val jumpBoost = buffer.readVarInt()

        return PlayerAction(entityId, type, jumpBoost)
    }

}

data class PlayerAction(val entityId: Int, val type: PlayerActionType, val jumpBoost: Int) : PacketMessage
