package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements

import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementConfig
import org.bukkit.Material

data class AutoSellConfig(
    override val enabled: Boolean,
    override val name: String,
    override val description: String,
    val priceList: Map<Material, Double>
): EnhancementConfig