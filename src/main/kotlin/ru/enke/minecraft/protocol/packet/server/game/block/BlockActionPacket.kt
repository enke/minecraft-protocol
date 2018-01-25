package ru.enke.minecraft.protocol.packet.server.game.block

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*
import ru.enke.minecraft.protocol.packet.data.game.Position

object BlockActionPacket : Packet<BlockAction> {

    override fun write(message: BlockAction, buffer: ByteBuf) {
        buffer.writePosition(message.position)
        buffer.writeByte(message.type)
        buffer.writeByte(message.value)
        buffer.writeVarInt(message.id and 0xFFF)
    }

    override fun read(buffer: ByteBuf): BlockAction {
        val position = buffer.readPosition()
        val type = buffer.readByte().toInt()
        val value = buffer.readByte().toInt()
        val id = buffer.readVarInt() and 0xFFF

        return BlockAction(position, type, value, id)
    }

}

data class BlockAction(val position: Position, val type: Int, val value: Int, val id: Int) : PacketMessage
