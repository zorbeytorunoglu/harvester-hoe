package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements

import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementConfig

data class HasteConfig(
    override val enabled: Boolean,
    val name: String,
    val description: String,
    val tiers: Map<Int, HasteTier>
): EnhancementConfig

data class HasteTier(
    val duration: Int,
    val level: Int
)