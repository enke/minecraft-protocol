package ru.enke.minecraft.protocol

import ru.enke.minecraft.protocol.packet.PacketDirection
import ru.enke.minecraft.protocol.packet.PacketDirection.INBOUND
import ru.enke.minecraft.protocol.packet.PacketDirection.OUTBOUND
import ru.enke.minecraft.protocol.packet.client.game.*
import ru.enke.minecraft.protocol.packet.client.game.block.BlockDigging
import ru.enke.minecraft.protocol.packet.client.game.block.BlockDiggingPacket
import ru.enke.minecraft.protocol.packet.client.game.block.BlockPlace
import ru.enke.minecraft.protocol.packet.client.game.block.BlockPlacePacket
import ru.enke.minecraft.protocol.packet.client.game.inventory.*
import ru.enke.minecraft.protocol.packet.client.game.position.*
import ru.enke.minecraft.protocol.packet.server.game.*
import ru.enke.minecraft.protocol.packet.server.game.block.*
import ru.enke.minecraft.protocol.packet.server.game.chunk.ChunkData
import ru.enke.minecraft.protocol.packet.server.game.chunk.ChunkDataPacket
import ru.enke.minecraft.protocol.packet.server.game.chunk.ChunkUnload
import ru.enke.minecraft.protocol.packet.server.game.chunk.ChunkUnloadPacket
import ru.enke.minecraft.protocol.packet.server.game.entity.*
import ru.enke.minecraft.protocol.packet.server.game.inventory.*

open class GameProtocol(direction: PacketDirection, direction2: PacketDirection) : SimpleProtocol() {
    init {
        registerPacket(direction, 0x00, TeleportConfirmPacket, TeleportConfirm::class)
        registerPacket(direction, 0x01, TabCompleteRequestPacket, TabCompleteRequest::class)
        registerPacket(direction, 0x02, ClientChatPacket, ClientChat::class)
        registerPacket(direction, 0x03, ClientStatusPacket, ClientStatus::class)
        registerPacket(direction, 0x04, ClientSettingsPacket, ClientSettings::class)
        registerPacket(direction, 0x05, ClientConfirmTransactionPacket, ClientConfirmTransaction::class)
        registerPacket(direction, 0x06, EnchantItemPacket, EnchantItem::class)
        registerPacket(direction, 0x07, InventoryClickPacket, InventoryClick::class)
        registerPacket(direction, 0x08, ClientInventoryClosePacket, ClientInventoryClose::class)
        registerPacket(direction, 0x09, ClientPluginMessagePacket, ClientPluginMessage::class)
        registerPacket(direction, 0x0A, InteractEntityPacket, InteractEntity::class)
        registerPacket(direction, 0x0B, ClientKeepAlivePacket, ClientKeepAlive::class)
        registerPacket(direction, 0x0C, PlayerPositionPacket, PlayerPosition::class)
        registerPacket(direction, 0x0D, ClientPlayerPositionLookPacket, ClientPlayerPositionLook::class)
        registerPacket(direction, 0x0E, PlayerLookPacket, PlayerLook::class)
        registerPacket(direction, 0x0F, PlayerGroundPacket, PlayerGround::class)
        registerPacket(direction, 0x10, ClientVehiclePositionPacket, ClientVehiclePosition::class)
        registerPacket(direction, 0x11, SteerBoatPacket, SteerBoat::class)
        registerPacket(direction, 0x12, CraftRecipeRequestPacket, CraftRecipeRequest::class)
        registerPacket(direction, 0x13, ClientPlayerAbilitiesPacket, ClientPlayerAbilities::class)
        registerPacket(direction, 0x14, BlockDiggingPacket, BlockDigging::class)
        registerPacket(direction, 0x15, PlayerActionPacket, PlayerAction::class)
        registerPacket(direction, 0x16, SteerVehiclePacket, SteerVehicle::class)
        registerPacket(direction, 0x17, CraftingBookDataPacket, CraftingBookData::class)
        registerPacket(direction, 0x18, ResourcePackStatusPacket, ResourcePackStatus::class)
        registerPacket(direction, 0x19, AdvancementTabPacket, AdvancementTab::class)
        registerPacket(direction, 0x1A, ClientItemHeldChangePacket, ClientItemHeldChange::class)
        registerPacket(direction, 0x1B, CreativeInventoryActionPacket, CreativeInventoryAction::class)
        registerPacket(direction, 0x1C, ClientUpdateSignPacket, ClientUpdateSign::class)
        registerPacket(direction, 0x1D, SwingArmPacket, SwingArm::class)
        registerPacket(direction, 0x1E, SpectatePacket, Spectate::class)
        registerPacket(direction, 0x1F, BlockPlacePacket, BlockPlace::class)
        registerPacket(direction, 0x20, UseItemPacket, UseItem::class)

        registerPacket(direction2, 0x00, SpawnObjectPacket, SpawnObject::class)
        registerPacket(direction2, 0x01, SpawnExpOrbPacket, SpawnExpOrb::class)
        registerPacket(direction2, 0x02, SpawnGlobalEntityPacket, SpawnGlobalEntity::class)
        registerPacket(direction2, 0x03, SpawnMobPacket, SpawnMob::class)
        registerPacket(direction2, 0x04, SpawnPaintingPacket, SpawnPainting::class)
        registerPacket(direction2, 0x05, SpawnPlayerPacket, SpawnPlayer::class)
        registerPacket(direction2, 0x06, AnimationPacket, Animation::class)
        registerPacket(direction2, 0x07, StatisticsPacket, Statistics::class)
        registerPacket(direction2, 0x08, BlockBreakAnimationPacket, BlockBreakAnimation::class)
        registerPacket(direction2, 0x09, TileEntityUpdatePacket, TileEntityUpdate::class)
        registerPacket(direction2, 0x0A, BlockActionPacket, BlockAction::class)
        registerPacket(direction2, 0x0B, BlockChangePacket, BlockChange::class)
        registerPacket(direction2, 0x0C, BossBarPacket, BossBar::class)
        registerPacket(direction2, 0x0D, DifficultyChangePacket, DifficultyChange::class)
        registerPacket(direction2, 0x0E, TabCompleteResponsePacket, TabCompleteResponse::class)
        registerPacket(direction2, 0x0F, ServerChatPacket, ServerChat::class)
        registerPacket(direction2, 0x10, MultiBlockChangePacket, MultiBlockChange::class)
        registerPacket(direction2, 0x12, ServerInventoryClosePacket, ServerInventoryClose::class)
        registerPacket(direction2, 0x13, InventoryOpenPacket, InventoryOpen::class)
        //TODO: Fix NBT read.
        //registerPacket(direction2, 0x14, InventoryItemsPacket, InventoryItems::class)
        registerPacket(direction2, 0x15, InventoryPropertyPacket, InventoryProperty::class)
        registerPacket(direction2, 0x16, InventorySetSlotPacket, InventorySetSlot::class)
        registerPacket(direction2, 0x17, SetCooldownPacket, SetCooldown::class)
        registerPacket(direction2, 0x18, ServerPluginMessagePacket, ServerPluginMessage::class)
        registerPacket(direction2, 0x19, PlaySoundPacket, PlaySound::class)
        registerPacket(direction2, 0x1A, DisconnectPacket, Disconnect::class)
        registerPacket(direction2, 0x1B, EntityStatusPacket, EntityStatus::class)
        registerPacket(direction2, 0x1C, ExplosionPacket, Explosion::class)
        registerPacket(direction2, 0x1D, ChunkUnloadPacket, ChunkUnload::class)
        registerPacket(direction2, 0x1F, ServerKeepAlivePacket, ServerKeepAlive::class)
        registerPacket(direction2, 0x20, ChunkDataPacket, ChunkData::class)
        registerPacket(direction2, 0x23, JoinGamePacket, JoinGame::class)
        registerPacket(direction2, 0x35, RespawnPacket, Respawn::class)
        registerPacket(direction2, 0x2C, ServerPlayerAbilitiesPacket, ServerPlayerAbilities::class)
        registerPacket(direction2, 0x38, WorldBorderPacket, WorldBorder::class)
        registerPacket(direction2, 0x3A, ServerItemHeldChangePacket, ServerItemHeldChange::class)
        registerPacket(direction2, 0x46, SpawnPositionPacket, SpawnPosition::class)
        registerPacket(direction2, 0x47, TimeUpdatePacket, TimeUpdate::class)
        registerPacket(direction2, 0x48, TitlePacket, Title::class)
    }
}

object ClientGameProtocol : GameProtocol(OUTBOUND, INBOUND)
object ServerGameProtocol : GameProtocol(INBOUND, OUTBOUND)
