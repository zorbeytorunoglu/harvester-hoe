package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements

import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementTierConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.TieredEnhancementConfig

data class SpeedBoostConfig(
    override val enabled: Boolean,
    override val name: String,
    override val description: String,
    override val tiers: Map<Int, SpeedBoostTier>
): TieredEnhancementConfig<SpeedBoostTier>

data class SpeedBoostTier(
    val duration: Int,
    val level: Int
): EnhancementTierConfig