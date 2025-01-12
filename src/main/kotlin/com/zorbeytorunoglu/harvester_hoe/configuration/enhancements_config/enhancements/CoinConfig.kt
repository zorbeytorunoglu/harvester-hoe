package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements

import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementTierConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.TieredEnhancementConfig

data class CoinConfig(
    override val enabled: Boolean,
    override val name: String,
    override val description: String,
    override val tiers: Map<Int, CoinTier>,
    val messageEnabled: Boolean,
    val message: String
): TieredEnhancementConfig<CoinTier>

data class CoinTier(
    val chance: Double,
    val command: String
): EnhancementTierConfig