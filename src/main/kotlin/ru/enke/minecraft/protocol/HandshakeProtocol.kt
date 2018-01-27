package ru.enke.minecraft.protocol

import ru.enke.minecraft.protocol.packet.PacketDirection
import ru.enke.minecraft.protocol.packet.PacketDirection.INBOUND
import ru.enke.minecraft.protocol.packet.PacketDirection.OUTBOUND
import ru.enke.minecraft.protocol.packet.client.handshake.Handshake
import ru.enke.minecraft.protocol.packet.client.handshake.HandshakePacket

open class HandshakeProtocol(direction: PacketDirection) : SimpleProtocol() {
    init {
        registerPacket(direction, 0x00, HandshakePacket, Handshake::class)
    }
}

object ClientHandshakeProtocol : HandshakeProtocol(OUTBOUND)
object ServerHandshakeProtocol : HandshakeProtocol(INBOUND)
