package com.zorbeytorunoglu.harvester_hoe.util

import com.zorbeytorunoglu.harvester_hoe.Core
import org.bukkit.entity.Player

fun Player.hasEnhancement(enhancementId: String): Boolean {
    return Core.services.enhancementService.hasEnhancement(
        playerUuid = uniqueId.toString(),
        enhancementId = enhancementId
    )
}

fun Player.hasEnhancementEnabled(enhancementId: String): Boolean =
    Core.services.enhancementService.isEnhancementEnabled(
        playerUuid = uniqueId.toString(),
        enhancementId = enhancementId
    )