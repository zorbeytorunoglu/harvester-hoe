package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config

import com.zorbeytorunoglu.harvester_hoe.configuration.Resource
import com.zorbeytorunoglu.harvester_hoe.configuration.ConfigLoader
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.HasteConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.SpeedBoostConfig

class EnhancementsConfigLoader: ConfigLoader<EnhancementsConfig> {
    override fun load(resource: Resource): EnhancementsConfig {
        return EnhancementsConfig(
            speedBoostConfig = SpeedBoostConfig(
                name = resource.getString("speed-boost.name") ?: "Speed Boost",
                description = resource.getString("speed-boost.description") ?: "Gives you speed boost!",
                enabled = resource.getBoolean("speed-boost.enabled", true),
                duration = resource.getString("speed-boost.duration")?.takeIf { it != "MAX" }?.toIntOrNull()
                    ?: Int.MAX_VALUE,
                level = resource.getInt("speed-boost.level", 1)
            ),
            hasteConfig = HasteConfig(
                name = resource.getString("speed-boost.name") ?: "Speed Boost",
                description = resource.getString("speed-boost.description") ?: "Gives you speed boost!",
                enabled = resource.getBoolean("speed-boost.enabled", true),
                duration = resource.getString("speed-boost.duration")?.takeIf { it != "MAX" }?.toIntOrNull()
                    ?: Int.MAX_VALUE,
                level = resource.getInt("speed-boost.level", 1)
            )
        )
    }
}