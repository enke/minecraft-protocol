package ru.enke.minecraft.protocol.packet.data.game

enum class BossBarAction {
    ADD, REMOVE, UPDATE_HEALTH, UPDATE_TITLE, UPDATE_STYLE, UPDATE_FLAGS
}

enum class BossBarColor {
    PINK, CYAN, RED, LIME, YELLOW, PURPLE, WHITE
}

enum class BossBarDivision {
    NONE, NOTCHES_6, NOTCHES_10, NOTCHES_12, NOTCHES_20
}