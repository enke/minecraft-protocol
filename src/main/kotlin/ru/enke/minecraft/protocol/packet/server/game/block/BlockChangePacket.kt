package ru.enke.minecraft.protocol.packet.server.game.block

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.BlockState
import ru.enke.minecraft.protocol.packet.data.game.Position

object BlockChangePacket : Packet<BlockChange> {

    override fun write(message: BlockChange, buffer: ByteBuf) {
        buffer.writePosition(message.position)
        buffer.writeBlockState(message.state)
    }

    override fun read(buffer: ByteBuf): BlockChange {
        val position = buffer.readPosition()
        val state = buffer.readBlockState()

        return BlockChange(position, state)
    }

}

data class BlockChange(val position: Position, val state: BlockState) : PacketMessage
