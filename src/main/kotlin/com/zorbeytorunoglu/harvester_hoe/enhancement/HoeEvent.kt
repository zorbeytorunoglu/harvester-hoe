package com.zorbeytorunoglu.harvester_hoe.enhancement

import org.bukkit.block.Block
import org.bukkit.entity.Player

sealed interface HoeEvent {
    data class OnHarvest(val player: Player, val block: Block): HoeEvent
    data class OnHold(val player: Player): HoeEvent
    data class OnStoppedHolding(val player: Player): HoeEvent
}