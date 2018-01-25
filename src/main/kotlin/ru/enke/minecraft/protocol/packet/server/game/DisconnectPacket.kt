package ru.enke.minecraft.protocol.packet.server.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.message.Message
import ru.enke.minecraft.protocol.packet.readString
import ru.enke.minecraft.protocol.packet.writeString

object DisconnectPacket : Packet<Disconnect> {

    override fun write(message: Disconnect, buffer: ByteBuf) {
        buffer.writeString(message.text.toJson())
    }

    override fun read(buffer: ByteBuf): Disconnect {
        return Disconnect(Message.fromJson(buffer.readString()))
    }

}

data class Disconnect(val text: Message) : PacketMessage
