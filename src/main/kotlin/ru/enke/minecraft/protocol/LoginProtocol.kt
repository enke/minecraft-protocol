package ru.enke.minecraft.protocol

import ru.enke.minecraft.protocol.packet.PacketDirection
import ru.enke.minecraft.protocol.packet.PacketDirection.INBOUND
import ru.enke.minecraft.protocol.packet.PacketDirection.OUTBOUND
import ru.enke.minecraft.protocol.packet.client.login.EncryptionResponse
import ru.enke.minecraft.protocol.packet.client.login.EncryptionResponsePacket
import ru.enke.minecraft.protocol.packet.client.login.LoginStart
import ru.enke.minecraft.protocol.packet.client.login.LoginStartPacket
import ru.enke.minecraft.protocol.packet.server.login.*

open class LoginProtocol(direction: PacketDirection, direction2: PacketDirection) : SimpleProtocol() {
    init {
        registerPacket(direction, 0x00, LoginStartPacket, LoginStart::class)
        registerPacket(direction, 0x01, EncryptionResponsePacket, EncryptionResponse::class)

        registerPacket(direction2, 0x00, LoginDisconnectPacket, LoginDisconnect::class)
        registerPacket(direction2, 0x01, EncryptionRequestPacket, EncryptionRequest::class)
        registerPacket(direction2, 0x02, LoginSuccessPacket, LoginSuccess::class)
        registerPacket(direction2, 0x03, LoginSetCompressionPacket, LoginSetCompression::class)
    }
}

object ClientLoginProtocol : LoginProtocol(OUTBOUND, INBOUND)
object ServerLoginProtocol : LoginProtocol(INBOUND, OUTBOUND)
