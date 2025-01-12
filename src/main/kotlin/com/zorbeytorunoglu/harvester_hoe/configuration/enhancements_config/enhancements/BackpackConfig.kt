package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements

import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementTierConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.TieredEnhancementConfig

data class BackpackConfig(
    override val enabled: Boolean,
    override val name: String,
    override val description: String,
    override val tiers: Map<Int, BackpackTier>,
    val messageEnabled: Boolean,
    val message: String
): TieredEnhancementConfig<BackpackTier>

data class BackpackTier(
    val size: Int
): EnhancementTierConfig