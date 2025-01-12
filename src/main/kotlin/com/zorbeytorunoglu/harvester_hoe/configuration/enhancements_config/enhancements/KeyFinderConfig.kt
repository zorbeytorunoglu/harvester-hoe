package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements

import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementTierConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.TieredEnhancementConfig

data class KeyFinderConfig(
    override val enabled: Boolean,
    override val name: String,
    override val description: String,
    override val tiers: Map<Int, KeyFinderTier>,
    val messageEnabled: Boolean,
    val message: String
): TieredEnhancementConfig<KeyFinderTier>

data class KeyFinderTier(
    val chance: Double,
    val command: String
): EnhancementTierConfig