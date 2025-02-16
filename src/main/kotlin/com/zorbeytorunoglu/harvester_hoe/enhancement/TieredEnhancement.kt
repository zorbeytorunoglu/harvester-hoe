package com.zorbeytorunoglu.harvester_hoe.enhancement

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementTierConfig
import org.bukkit.entity.Player

interface TieredEnhancement <T: EnhancementTierConfig>: Enhancement {

    val tiers: Map<Int, T>

    fun getTier(playerUuid: String): T? =
        Core.services.enhancementService.getEnhancementLevel(playerUuid, id).let {
            tiers[it]
        }

    fun getTier(player: Player): T? =
        getTier(player.uniqueId.toString())

}