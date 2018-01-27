package ru.enke.minecraft.protocol

import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketDirection
import ru.enke.minecraft.protocol.packet.PacketMessage

interface Protocol {

    companion object {
        // Current protocol version.
        const val VERSION = 340 // 1.12.2
    }

    fun <T : PacketMessage> getPacketByMessage(direction: PacketDirection, message: T): Packet<T>
    fun getIdByPacket(direction: PacketDirection, packet: Packet<*>): Byte
    fun getPacketById(direction: PacketDirection, id: Byte): Packet<*>?

}
