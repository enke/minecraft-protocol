package ru.enke.minecraft.protocol.packet.server.login

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.readString
import ru.enke.minecraft.protocol.packet.writeString

object LoginSuccessPacket : Packet<LoginSuccess> {

    override fun write(message: LoginSuccess, buffer: ByteBuf) {
        buffer.writeString(message.playerId)
        buffer.writeString(message.playerName)
    }

    override fun read(buffer: ByteBuf): LoginSuccess {
        val playerId = buffer.readString()
        val playerName = buffer.readString()

        return LoginSuccess(playerId, playerName)
    }

}

data class LoginSuccess(var playerId: String, var playerName: String) : PacketMessage
