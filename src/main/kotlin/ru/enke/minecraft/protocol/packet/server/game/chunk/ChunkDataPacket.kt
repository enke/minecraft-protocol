package ru.enke.minecraft.protocol.packet.server.game.chunk

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*

object ChunkDataPacket : Packet<ChunkData> {

    override fun write(message: ChunkData, buffer: ByteBuf) {
        buffer.writeInt(message.x)
        buffer.writeInt(message.z)
        buffer.writeBoolean(message.full)
        buffer.writeVarInt(message.mask)
        buffer.writeByteArray(message.data)
    }

    override fun read(buffer: ByteBuf): ChunkData {
        val x = buffer.readInt()
        val z = buffer.readInt()
        val full = buffer.readBoolean()
        val mask = buffer.readVarInt()
        val data = buffer.readByteArray()

        return ChunkData(x, z, full, mask, data)
    }

}

data class ChunkData(val x: Int, val z: Int, val full: Boolean, val mask: Int, val data: ByteArray) : PacketMessage
