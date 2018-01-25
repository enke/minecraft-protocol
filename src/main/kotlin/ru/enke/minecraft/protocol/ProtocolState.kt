package ru.enke.minecraft.protocol

import ru.enke.minecraft.protocol.ProtocolSide.CLIENT
import ru.enke.minecraft.protocol.ProtocolSide.SERVER

/**
 * Possible protocol states.
 */
enum class ProtocolState(val clientProtocol: ru.enke.minecraft.protocol.Protocol, val serverProtocol: ru.enke.minecraft.protocol.Protocol) {

    /**
     * The handshake protocol from which all network communication begins.
     *
     * The client must send the packet and set the protocol mode with
     * which he wants to communicate.
     */
    HANDSHAKE(ru.enke.minecraft.protocol.ClientHandshakeProtocol, ru.enke.minecraft.protocol.ServerHandshakeProtocol),

    /**
     * An auxiliary protocol for obtaining information about the status of the server.
     *
     * Does not require authorization.
     */
    STATUS(ru.enke.minecraft.protocol.ClientStatusProtocol, ru.enke.minecraft.protocol.ServerStatusProtocol),

    /**
     * Protocol for logging on to the server.
     *
     * The client must be authorized before he can log into the server.
     */
    LOGIN(ru.enke.minecraft.protocol.ClientLoginProtocol, ru.enke.minecraft.protocol.ServerLoginProtocol),

    /**
     * Directly the game protocol itself, with which the client communicates
     * when it comes to the server.
     */
    GAME(ru.enke.minecraft.protocol.ClientGameProtocol, ru.enke.minecraft.protocol.ServerGameProtocol);

    fun getProtocolBySide(side: ru.enke.minecraft.protocol.ProtocolSide): ru.enke.minecraft.protocol.Protocol {
        return when(side) {
            CLIENT -> clientProtocol
            SERVER -> serverProtocol
        }
    }

}
