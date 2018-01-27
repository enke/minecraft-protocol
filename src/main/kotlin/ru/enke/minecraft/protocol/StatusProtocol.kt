package ru.enke.minecraft.protocol

import ru.enke.minecraft.protocol.packet.PacketDirection
import ru.enke.minecraft.protocol.packet.PacketDirection.INBOUND
import ru.enke.minecraft.protocol.packet.PacketDirection.OUTBOUND
import ru.enke.minecraft.protocol.packet.client.status.PingRequest
import ru.enke.minecraft.protocol.packet.client.status.PingRequestPacket
import ru.enke.minecraft.protocol.packet.client.status.StatusRequest
import ru.enke.minecraft.protocol.packet.client.status.StatusRequestPacket
import ru.enke.minecraft.protocol.packet.server.status.PingResponse
import ru.enke.minecraft.protocol.packet.server.status.PingResponsePacket
import ru.enke.minecraft.protocol.packet.server.status.StatusResponse
import ru.enke.minecraft.protocol.packet.server.status.StatusResponsePacket

open class StatusProtocol(direction: PacketDirection, direction2: PacketDirection) : SimpleProtocol() {
    init {
        registerPacket(direction, 0x00, StatusRequestPacket, StatusRequest::class)
        registerPacket(direction, 0x01, PingRequestPacket, PingRequest::class)

        registerPacket(direction2, 0x00, StatusResponsePacket, StatusResponse::class)
        registerPacket(direction2, 0x01, PingResponsePacket, PingResponse::class)
    }
}

object ClientStatusProtocol : StatusProtocol(OUTBOUND, INBOUND)
object ServerStatusProtocol : StatusProtocol(INBOUND, OUTBOUND)
