package ru.enke.minecraft.protocol.packet.data.message

data class HoverEvent(val action: HoverAction, val value: String)
data class ClickEvent(val action: ClickAction, val value: String)
