package ru.enke.minecraft.protocol.packet.server.login

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.message.Message
import ru.enke.minecraft.protocol.packet.readString
import ru.enke.minecraft.protocol.packet.writeString

object LoginDisconnectPacket : Packet<LoginDisconnect> {

    override fun write(message: LoginDisconnect, buffer: ByteBuf) {
        buffer.writeString(message.text.toJson())
    }

    override fun read(buffer: ByteBuf): LoginDisconnect {
        return LoginDisconnect(Message.fromJson(buffer.readString()))
    }

}

data class LoginDisconnect(val text: Message) : PacketMessage
