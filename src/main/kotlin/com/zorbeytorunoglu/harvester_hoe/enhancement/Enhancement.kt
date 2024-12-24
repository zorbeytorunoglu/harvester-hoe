package com.zorbeytorunoglu.harvester_hoe.enhancement

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementConfig
import org.bukkit.entity.Player

interface Enhancement {
    val id: String
    val config: EnhancementConfig
    val name: String
    val description: String

    fun isEnabledForPlayer(player: Player): Boolean =
        Core.playerDataManager.enhancementService.isEnhancementEnabled(player, id)

    fun canHandle(event: HoeEvent): Boolean
    fun handle(event: HoeEvent)
}

internal inline fun <T: HoeEvent> T.with(block: T.() -> Unit) { block() }