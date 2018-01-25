package ru.enke.minecraft.protocol.packet.client.game

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage

object EnchantItemPacket : Packet<EnchantItem> {

    override fun write(message: EnchantItem, buffer: ByteBuf) {
        buffer.writeByte(message.windowId)
        buffer.writeByte(message.enchantment)
    }

    override fun read(buffer: ByteBuf): EnchantItem {
        val windowId = buffer.readByte().toInt()
        val enchantment = buffer.readByte().toInt()

        return EnchantItem(windowId, enchantment)
    }

}

data class EnchantItem(val windowId: Int, val enchantment: Int) : PacketMessage