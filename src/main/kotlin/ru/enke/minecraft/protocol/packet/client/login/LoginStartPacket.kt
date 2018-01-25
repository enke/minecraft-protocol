package ru.enke.minecraft.protocol.packet.client.login

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.readString
import ru.enke.minecraft.protocol.packet.writeString

object LoginStartPacket : Packet<LoginStart> {

    override fun write(message: LoginStart, buffer: ByteBuf) {
        buffer.writeString(message.username)
    }

    override fun read(buffer: ByteBuf): LoginStart {
        return LoginStart(buffer.readString())
    }

}

data class LoginStart(val username: String) : PacketMessage
