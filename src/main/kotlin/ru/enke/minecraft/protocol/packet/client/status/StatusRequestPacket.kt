package ru.enke.minecraft.protocol.packet.client.status

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object StatusRequestPacket : Packet<StatusRequest> {

    override fun write(message: StatusRequest, buffer: ByteBuf) {

    }

    override fun read(buffer: ByteBuf): StatusRequest {
        return StatusRequest()
    }

}

class StatusRequest : PacketMessage {

    override fun toString(): String {
        return "StatusRequest()"
    }

}
