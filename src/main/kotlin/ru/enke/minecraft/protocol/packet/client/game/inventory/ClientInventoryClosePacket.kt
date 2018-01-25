package ru.enke.minecraft.protocol.packet.client.game.inventory

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object ClientInventoryClosePacket : Packet<ClientInventoryClose> {

    override fun write(message: ClientInventoryClose, buffer: ByteBuf) {
        buffer.writeByte(message.windowId)
    }

    override fun read(buffer: ByteBuf): ClientInventoryClose {
        return ClientInventoryClose(buffer.readByte().toInt())
    }

}

data class ClientInventoryClose(val windowId: Int) : PacketMessage
