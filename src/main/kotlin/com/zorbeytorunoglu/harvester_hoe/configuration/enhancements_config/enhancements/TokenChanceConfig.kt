package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements

import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.TieredEnhancementConfig

data class TokenChanceConfig(
    override val enabled: Boolean,
    override val name: String,
    override val description: String,
    val messageEnabled: Boolean,
    val message: String,
    override val tiers: Map<Int, TokenChanceTier>
): TieredEnhancementConfig<TokenChanceTier>

data class TokenChanceTier(
    val chance: Double
)