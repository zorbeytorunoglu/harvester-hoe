package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements

import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementConfig

data class SpeedBoostConfig(
    override val enabled: Boolean,
    val name: String,
    val description: String,
    val tiers: Map<Int, SpeedBoostTier>
): EnhancementConfig

data class SpeedBoostTier(
    val duration: Int,
    val level: Int
)