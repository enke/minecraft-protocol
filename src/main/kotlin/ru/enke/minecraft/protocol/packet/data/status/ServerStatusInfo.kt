package ru.enke.minecraft.protocol.packet.data.status

import ru.enke.minecraft.protocol.packet.data.message.Message
import java.util.*

data class ServerStatusInfo @JvmOverloads constructor(val version: Version, val description: Message, val players: Players, val favicon: String? = null)
data class Version(val name: String, val protocol: Int)
data class Players @JvmOverloads constructor(val online: Int, val max: Int, val list: List<Player> = Collections.emptyList())
data class Player(val id: UUID, val name: String)