package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements

import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementConfig

data class AutoCollectConfig(
    override val enabled: Boolean,
    override val name: String,
    override val description: String
): EnhancementConfig
