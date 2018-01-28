package ru.enke.minecraft.protocol.packet.data.game

data class EntityMetadata(val id: Int, val type: EntityMetadataType, val value: Any?)

enum class EntityMetadataType {
    BYTE,
    INT,
    FLOAT,
    STRING,
    CHAT,
    SLOT,
    BOOLEAN,
    ROTATION,
    POSITION,
    OPTIONAL_POSITION,
    BLOCK_FACE,
    OPTIONAL_UUID,
    BLOCK_STATE,
    NBT_TAG
}
