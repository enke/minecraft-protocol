package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object ClientConfirmTransactionPacket : Packet<ClientConfirmTransaction> {

    override fun write(message: ClientConfirmTransaction, buffer: ByteBuf) {
        buffer.writeByte(message.windowId)
        buffer.writeShort(message.actionId)
        buffer.writeBoolean(message.accepted)
    }

    override fun read(buffer: ByteBuf): ClientConfirmTransaction {
        val windowId = buffer.readByte().toInt()
        val actionId = buffer.readShort().toInt()
        val accepted = buffer.readBoolean()

        return ClientConfirmTransaction(windowId, actionId, accepted)
    }

}

data class ClientConfirmTransaction(val windowId: Int, val actionId: Int, val accepted: Boolean) : PacketMessage