package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements

import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.TieredEnhancementConfig

data class FortuneConfig(
    override val enabled: Boolean,
    override val name: String,
    override val description: String,
    override val tiers: Map<Int, FortuneTier>,
    val messageEnabled: Boolean,
    val message: String
): TieredEnhancementConfig<FortuneTier>

data class FortuneTier(
    val chance: Double,
    val amount: Int
)