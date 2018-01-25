package ru.enke.minecraft.protocol.packet.server.login

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.*

object EncryptionRequestPacket : Packet<EncryptionRequest> {

    override fun write(message: EncryptionRequest, buffer: ByteBuf) {
        buffer.writeString(message.serverId)
        buffer.writeByteArray(message.publicKey)
        buffer.writeByteArray(message.verifyToken)
    }

    override fun read(buffer: ByteBuf): EncryptionRequest {
        val serverId = buffer.readString()
        val publicKey = buffer.readByteArray()
        val verifyToken = buffer.readByteArray()

        return EncryptionRequest(serverId, publicKey, verifyToken)
    }

}

data class EncryptionRequest(val serverId: String, val publicKey: ByteArray, val verifyToken: ByteArray) : PacketMessage
