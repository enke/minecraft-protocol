package ru.enke.minecraft.protocol.packet.server.game.block

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.Position

object BlockBreakAnimationPacket : Packet<BlockBreakAnimation> {

    override fun write(message: BlockBreakAnimation, buffer: ByteBuf) {
        buffer.writeVarInt(message.entityId)
        buffer.writePosition(message.position)
        buffer.writeByte(message.stage)
    }

    override fun read(buffer: ByteBuf): BlockBreakAnimation {
        val entityId = buffer.readVarInt()
        val position = buffer.readPosition()
        val stage = buffer.readByte().toInt()

        return BlockBreakAnimation(entityId, position, stage)
    }

}

data class BlockBreakAnimation(val entityId: Int, val position: Position, val stage: Int) : PacketMessage