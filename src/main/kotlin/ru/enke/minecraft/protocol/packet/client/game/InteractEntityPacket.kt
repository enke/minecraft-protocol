package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.Hand
import ru.enke.minecraft.protocol.packet.data.game.InteractType
import ru.enke.minecraft.protocol.packet.data.game.InteractType.INTERACT
import ru.enke.minecraft.protocol.packet.data.game.InteractType.INTERACT_AT

object InteractEntityPacket : Packet<InteractEntity> {

    override fun write(message: InteractEntity, buffer: ByteBuf) {
        buffer.writeVarInt(message.entityId)
        buffer.writeEnum(message.type)

        if(message.type == INTERACT_AT) {
            buffer.writeFloat(message.targetX!!)
            buffer.writeFloat(message.targetY!!)
            buffer.writeFloat(message.targetZ!!)
        }

        if(message.type == INTERACT || message.type == INTERACT_AT) {
            buffer.writeEnum(message.hand!!)
        }
    }

    override fun read(buffer: ByteBuf): InteractEntity {
        val entityId = buffer.readVarInt()
        val type = buffer.readEnum<InteractType>()
        var targetX: Float? = 0F
        var targetY: Float? = 0F
        var targetZ: Float? = 0F
        var hand: Hand? = null

        if(type === INTERACT_AT) {
            targetX = buffer.readFloat()
            targetY = buffer.readFloat()
            targetZ = buffer.readFloat()
        }

        if(type == INTERACT || type == INTERACT_AT) {
            hand = buffer.readEnum<Hand>()
        }

        return InteractEntity(entityId, type, targetX, targetY, targetZ, hand)
    }

}

data class InteractEntity(val entityId: Int, val type: InteractType, val targetX: Float?,
                          val targetY: Float?, val targetZ: Float?, val hand: Hand?) : PacketMessage
