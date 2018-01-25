package ru.enke.minecraft.protocol.packet.server.game.chunk

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object ChunkUnloadPacket : Packet<ChunkUnload> {

    override fun write(message: ChunkUnload, buffer: ByteBuf) {
        buffer.writeInt(message.x)
        buffer.writeInt(message.z)
    }

    override fun read(buffer: ByteBuf): ChunkUnload {
        val x = buffer.readInt()
        val z = buffer.readInt()

        return ChunkUnload(x, z)
    }

}

data class ChunkUnload(val x: Int, val z: Int) : PacketMessage
