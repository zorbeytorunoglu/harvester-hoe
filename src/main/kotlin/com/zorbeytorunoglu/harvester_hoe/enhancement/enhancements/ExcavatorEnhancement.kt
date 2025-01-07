package com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.ExcavatorConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.ExcavatorTier
import com.zorbeytorunoglu.harvester_hoe.enhancement.TieredEnhancement
import com.zorbeytorunoglu.harvester_hoe.event.HoeEvent

const val EXCAVATOR_ENHANCEMENT_ID = "excavator"

class ExcavatorEnhancement: TieredEnhancement<ExcavatorTier, ExcavatorConfig> {

    override val id: String = EXCAVATOR_ENHANCEMENT_ID
    override val config: ExcavatorConfig = Core.enhancementsConfigManager.get().excavatorConfig
    override val name: String = config.name
    override val description: String = config.name
    override val tiers: Map<Int, ExcavatorTier> = config.tiers

    override fun canHandle(event: HoeEvent): Boolean = when (event) {
        is HoeEvent.OnHarvest -> isEnabledForPlayer(event.player)
        else -> false
    }

    override fun handle(event: HoeEvent) {



    }

}