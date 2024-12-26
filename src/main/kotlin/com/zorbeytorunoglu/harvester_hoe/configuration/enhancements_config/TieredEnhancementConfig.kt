package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config

interface TieredEnhancementConfig <T>: EnhancementConfig {
    val tiers: Map<Int, T>
}