package ru.enke.minecraft.protocol.packet.client.status

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object PingRequestPacket : Packet<PingRequest> {

    override fun write(message: PingRequest, buffer: ByteBuf) {
        buffer.writeLong(message.time)
    }

    override fun read(buffer: ByteBuf): PingRequest {
        return PingRequest(buffer.readLong())
    }

}

data class PingRequest(val time: Long) : PacketMessage
