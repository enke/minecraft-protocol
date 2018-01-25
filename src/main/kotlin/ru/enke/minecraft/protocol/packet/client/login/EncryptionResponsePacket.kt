package ru.enke.minecraft.protocol.packet.client.login

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.readByteArray
import ru.enke.minecraft.protocol.packet.writeByteArray

object EncryptionResponsePacket : Packet<EncryptionResponse> {

    override fun write(message: EncryptionResponse, buffer: ByteBuf) {
        buffer.writeByteArray(message.sharedKey)
        buffer.writeByteArray(message.verifyToken)
    }

    override fun read(buffer: ByteBuf): EncryptionResponse {
        val sharedKey = buffer.readByteArray()
        val verifyToken = buffer.readByteArray()

        return EncryptionResponse(sharedKey, verifyToken)
    }

}

data class EncryptionResponse(val sharedKey: ByteArray, val verifyToken: ByteArray) : PacketMessage
