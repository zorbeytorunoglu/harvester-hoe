package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements

import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementTierConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.TieredEnhancementConfig

data class ExcavatorConfig(
    override val enabled: Boolean,
    override val name: String,
    override val description: String,
    override val tiers: Map<Int, ExcavatorTier>
): TieredEnhancementConfig<ExcavatorTier>

data class ExcavatorTier(
    val radius: Int,
    val chance: Double
): EnhancementTierConfig