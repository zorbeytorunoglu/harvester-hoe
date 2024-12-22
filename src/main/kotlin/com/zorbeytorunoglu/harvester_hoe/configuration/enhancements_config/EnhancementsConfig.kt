package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config

import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.HasteConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.SpeedBoostConfig

data class EnhancementsConfig(
    val speedBoostConfig: SpeedBoostConfig,
    val hasteConfig: HasteConfig
)



