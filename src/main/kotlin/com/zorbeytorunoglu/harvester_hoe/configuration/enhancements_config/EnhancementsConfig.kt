package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config

import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.AutoCollectConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.AutoSellConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.BackpackConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.HasteConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.KeyFinderConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.SpeedBoostConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.TokenChanceConfig

data class EnhancementsConfig(
    val speedBoostConfig: SpeedBoostConfig,
    val hasteConfig: HasteConfig,
    val autoCollectConfig: AutoCollectConfig,
    val tokenChanceConfig: TokenChanceConfig,
    val autoSellConfig: AutoSellConfig,
    val backpackConfig: BackpackConfig,
    val keyFinderConfig: KeyFinderConfig,
    val enhancementTierCount: Map<String, Int>
)