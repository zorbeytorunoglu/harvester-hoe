package com.zorbeytorunoglu.harvester_hoe.event

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent

sealed interface HoeEvent {
    data class OnHarvest(val player: Player, val block: Block, val event: BlockBreakEvent): HoeEvent
    data class OnHold(val player: Player): HoeEvent
    data class OnStoppedHolding(val player: Player): HoeEvent
    data class OnBackpackStore(val player: Player, val amount: Int): HoeEvent
    data class OnInventoryStore(val player: Player, val type: Material, val amount: Int): HoeEvent
    data class OnHarvestCollected(val player: Player, val type: Material, val amount: Int): HoeEvent
    data class OnAutoCollect(
        val player: Player,
        val block: Block,
        val excludeBottommost: Boolean,
        val event: BlockBreakEvent
    ): HoeEvent
}