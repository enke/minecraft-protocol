package ru.enke.minecraft.protocol.packet.server.status

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object PingResponsePacket : Packet<PingResponse> {

    override fun write(message: PingResponse, buffer: ByteBuf) {
        buffer.writeLong(message.time)
    }

    override fun read(buffer: ByteBuf): PingResponse {
        return PingResponse(buffer.readLong())
    }

}

data class PingResponse(val time: Long) : PacketMessage
