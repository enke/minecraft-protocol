package ru.enke.minecraft.protocol.packet.client.game.inventory

import io.netty.buffer.ByteBuf
import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.data.game.Slot
import ru.enke.minecraft.protocol.packet.readSlot
import ru.enke.minecraft.protocol.packet.writeSlot

object CreativeInventoryActionPacket : Packet<CreativeInventoryAction> {

    override fun write(message: CreativeInventoryAction, buffer: ByteBuf) {
        buffer.writeShort(message.slotIndex)
        buffer.writeSlot(message.slot)
    }

    override fun read(buffer: ByteBuf): CreativeInventoryAction {
        val slotIndex = buffer.readShort().toInt()
        val slot = buffer.readSlot()

        return CreativeInventoryAction(slotIndex, slot)
    }

}

data class CreativeInventoryAction(val slotIndex: Int, val slot: Slot?) : PacketMessage
