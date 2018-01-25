package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.AnimationType

object AnimationPacket : Packet<Animation> {

    override fun write(message: Animation, buffer: ByteBuf) {
        buffer.writeVarInt(message.entityId)
        buffer.writeEnum(message.type)
    }

    override fun read(buffer: ByteBuf): Animation {
        val entityId = buffer.readVarInt()
        val type = buffer.readEnum<AnimationType>()

        return Animation(entityId, type)
    }

}

data class Animation(val entityId: Int, val type: AnimationType) : PacketMessage
