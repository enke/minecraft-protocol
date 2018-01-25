package ru.enke.minecraft.protocol.packet.client.game.block

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.DiggingAction
import ru.enke.minecraft.protocol.packet.data.game.Direction
import ru.enke.minecraft.protocol.packet.data.game.Position

object BlockDiggingPacket : Packet<BlockDigging> {

    override fun write(message: BlockDigging, buffer: ByteBuf) {
        buffer.writeEnum(message.action)
        buffer.writePosition(message.position)
        buffer.writeEnum(message.direction)
    }

    override fun read(buffer: ByteBuf): BlockDigging {
        val action = buffer.readEnum<DiggingAction>()
        val position = buffer.readPosition()
        val direction = buffer.readEnum<Direction>()

        return BlockDigging(action, position, direction)
    }

}

data class BlockDigging(val action: DiggingAction, val position: Position, val direction: Direction) : PacketMessage