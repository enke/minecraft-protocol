package ru.enke.minecraft.protocol

import ru.enke.minecraft.protocol.ProtocolSide.CLIENT
import ru.enke.minecraft.protocol.ProtocolSide.SERVER

/**
 * Possible protocol states.
 */
enum class ProtocolState(val clientProtocol: Protocol, val serverProtocol: Protocol) {

    /**
     * The handshake protocol from which all network communication begins.
     *
     * The client must send the packet and set the protocol mode with
     * which he wants to communicate.
     */
    HANDSHAKE(ClientHandshakeProtocol, ServerHandshakeProtocol),

    /**
     * An auxiliary protocol for obtaining information about the status of the server.
     *
     * Does not require authorization.
     */
    STATUS(ClientStatusProtocol, ServerStatusProtocol),

    /**
     * Protocol for logging on to the server.
     *
     * The client must be authorized before he can log into the server.
     */
    LOGIN(ClientLoginProtocol, ServerLoginProtocol),

    /**
     * Directly the game protocol itself, with which the client communicates
     * when it comes to the server.
     */
    GAME(ClientGameProtocol, ServerGameProtocol);

    fun getProtocolBySide(side: ProtocolSide): Protocol {
        return when(side) {
            CLIENT -> clientProtocol
            SERVER -> serverProtocol
        }
    }

}
