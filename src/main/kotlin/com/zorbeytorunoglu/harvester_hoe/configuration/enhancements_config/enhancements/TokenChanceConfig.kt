package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements

import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementConfig

data class TokenChanceConfig(
    override val enabled: Boolean,
    val name: String,
    val description: String,
    val messageEnabled: Boolean,
    val message: String,
    val tiers: Map<Int, TokenChanceTier>
): EnhancementConfig

data class TokenChanceTier(
    val chance: Double
)