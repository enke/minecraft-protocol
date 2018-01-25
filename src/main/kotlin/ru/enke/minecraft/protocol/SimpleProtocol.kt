package ru.enke.minecraft.protocol

import ru.enke.minecraft.protocol.packet.Packet
import ru.enke.minecraft.protocol.packet.PacketDirection
import ru.enke.minecraft.protocol.packet.PacketDirection.INBOUND
import ru.enke.minecraft.protocol.packet.PacketDirection.OUTBOUND
import ru.enke.minecraft.protocol.packet.PacketMessage
import kotlin.reflect.KClass

open class SimpleProtocol : ru.enke.minecraft.protocol.Protocol {
    private val inbound = ru.enke.minecraft.protocol.SimpleProtocol.DirectionHandler()
    private val outbound = ru.enke.minecraft.protocol.SimpleProtocol.DirectionHandler()

    private class DirectionHandler {
        val idToPacket = HashMap<Byte, Packet<*>>()
        val packetToId = HashMap<Packet<*>, Byte>()
        val messageToPacket = HashMap<KClass<out PacketMessage>, Packet<*>>()
    }

    private fun getHandler(direction: PacketDirection): ru.enke.minecraft.protocol.SimpleProtocol.DirectionHandler {
        when(direction) {
            INBOUND -> return inbound
            OUTBOUND -> return outbound
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : PacketMessage> getPacketByMessage(direction: PacketDirection, message: T): Packet<T> {
        val packet = getHandler(direction).messageToPacket[message::class] ?:
                throw IllegalArgumentException("Not found packet for message")

        return packet as Packet<T>
    }

    override fun getIdByPacket(direction: PacketDirection, packet: Packet<*>): Byte {
        return getHandler(direction).packetToId[packet]!!
    }

    override fun getPacketById(direction: PacketDirection, id: Byte): Packet<*>? {
        return getHandler(direction).idToPacket[id]
    }

    fun registerPacket(direction: PacketDirection, id: Byte, packet: Packet<*>, message: KClass<out PacketMessage>) {
        val handler = getHandler(direction)

        if(handler.idToPacket.containsKey(id)) {
            throw IllegalStateException("Packet with id $id already exists")
        }

        handler.idToPacket.put(id, packet)
        handler.packetToId.put(packet, id)
        handler.messageToPacket.put(message, packet)
    }

}
