package com.zorbeytorunoglu.harvester_hoe.config

data class EnhancementsConfig(
    val speedBoostConfig: SpeedBoostConfig
)

data class SpeedBoostConfig(
    val duration: String,
    val level: Int
)

fun Resource.loadEnhancementsConfig(): EnhancementsConfig {
    load()
    return EnhancementsConfig(
        SpeedBoostConfig(
            getString("speed-boost.duration")!!,
            getInt("speed-boost.level")
        )
    )
}