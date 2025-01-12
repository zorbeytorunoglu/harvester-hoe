package com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.enhancement.TieredEnhancement
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.BackpackConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.BackpackTier
import com.zorbeytorunoglu.harvester_hoe.event.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.enhancement.with

const val BACKPACK_ENHANCEMENT_ID = "backpack"

class BackpackEnhancement: TieredEnhancement<BackpackTier> {

    override val id: String = BACKPACK_ENHANCEMENT_ID
    override val config: BackpackConfig = Core.enhancementsConfigManager.get().backpackConfig
    override val name: String = config.name
    override val description: String = config.description
    override val tiers: Map<Int, BackpackTier> = config.tiers

    override fun canHandle(event: HoeEvent): Boolean = when (event) {
        is HoeEvent.OnBackpackStore -> isEnabledForPlayer(event.player)
        else -> false
    }

    override fun handle(event: HoeEvent) = (event as HoeEvent.OnBackpackStore).with {
        val backpackSize = getTier(event.player)?.size ?: return@with
        val harvestsInBackpack = Core.services.playerDataService.getHarvestsInBackpack(event.player.uniqueId.toString())

        if (harvestsInBackpack >= backpackSize) {
            if (config.messageEnabled)
                event.player.sendMessage(config.message)
            return@with
        }

        Core.services.playerDataService.addHarvestsToBackpack(player.uniqueId.toString(), event.amount)
    }
}