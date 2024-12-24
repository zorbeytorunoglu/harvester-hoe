package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config

import com.zorbeytorunoglu.harvester_hoe.configuration.Resource
import com.zorbeytorunoglu.harvester_hoe.configuration.ConfigLoader
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.AutoCollectConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.AutoSellConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.HasteConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.HasteTier
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.SpeedBoostConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.SpeedBoostTier
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.TokenChanceConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.TokenChanceTier
import com.zorbeytorunoglu.harvester_hoe.util.colorHex
import org.bukkit.Material

class EnhancementsConfigLoader: ConfigLoader<EnhancementsConfig> {
    override fun load(resource: Resource): EnhancementsConfig {
        return EnhancementsConfig(
            speedBoostConfig = SpeedBoostConfig(
                name = (resource.getString("speed-boost.name") ?: "Speed Boost").colorHex,
                description = (resource.getString("speed-boost.description") ?: "Gives you speed boost!").colorHex,
                enabled = resource.getBoolean("speed-boost.enabled", true),
                tiers = resource.getConfigurationSection("speed-boost.tiers")
                    ?.getKeys(false)
                    ?.mapNotNull { tierKey ->
                        tierKey.toIntOrNull()?.let { tierNumber ->
                            tierNumber to SpeedBoostTier(
                                duration = resource.getString("speed-boost.tiers.$tierKey.duration")
                                    ?.takeIf { it != "MAX" }
                                    ?.toIntOrNull()
                                    ?: Int.MAX_VALUE,
                                level = resource.getInt("speed-boost.tiers.$tierKey.level", 1)
                            )
                        }
                    }?.toMap() ?: emptyMap()
            ),
            hasteConfig = HasteConfig(
                name = (resource.getString("haste.name") ?: "Speed Boost").colorHex,
                description = (resource.getString("haste.description") ?: "Gives you speed boost!").colorHex,
                enabled = resource.getBoolean("haste.enabled", true),
                tiers = resource.getConfigurationSection("haste.tiers")
                    ?.getKeys(false)
                    ?.mapNotNull { tierKey ->
                        tierKey.toIntOrNull()?.let { tierNumber ->
                            tierNumber to HasteTier(
                                duration = resource.getString("haste.tiers.$tierKey.duration")
                                    ?.takeIf { it != "MAX" }
                                    ?.toIntOrNull()
                                    ?: Int.MAX_VALUE,
                                level = resource.getInt("haste.tiers.$tierKey.level", 1)
                            )
                        }
                    }?.toMap() ?: emptyMap()
            ),
            autoCollectConfig = AutoCollectConfig(
                enabled = resource.getBoolean("auto-collect.enabled", true),
                name = (resource.getString("auto-collect.name") ?: "Auto Collect").colorHex,
                description = (resource.getString("auto-collect.description") ?: "Automatically collects harvests!").colorHex
            ),
            tokenChanceConfig = TokenChanceConfig(
                enabled = resource.getBoolean("token-chance.enabled", true),
                name = (resource.getString("token-chance.name") ?: "Token Chance").colorHex,
                description = (resource.getString("token-chance.description") ?: "Gives you a chance to get tokens!").colorHex,
                messageEnabled = resource.getBoolean("token-chance.message-enabled", true),
                message = (resource.getString("token-chance.message") ?: "&aYou found a token!").colorHex,
                tiers = resource.getConfigurationSection("token-chance.tiers")
                    ?.getKeys(false)
                    ?.mapNotNull { tierKey ->
                        tierKey.toIntOrNull()?.let { tierNumber ->
                            tierNumber to TokenChanceTier(
                                chance = resource.getDouble("token-chance.tiers.$tierKey.chance", 0.0)
                            )
                        }
                    }?.toMap() ?: emptyMap()
            ),
            autoSellConfig = AutoSellConfig(
                enabled = resource.getBoolean("auto-sell.enabled", true),
                name = (resource.getString("auto-sell.name") ?: "Auto Sell").colorHex,
                description = (resource.getString("auto-sell.description") ?: "Automatically sells items!").colorHex,
                priceList = resource.getConfigurationSection("auto-sell.price-list")?.getKeys(false)
                    ?.mapNotNull { Material.valueOf(it) }
                    ?.associateWith { resource.getDouble("auto-sell.price-list.$it", 0.0) } ?: emptyMap()
            ),
            enhancementTierCount = resource.getKeys(false).associateWith { enhId ->
                resource.getConfigurationSection("$enhId.tiers")?.getKeys(false)?.size ?: 0
            }
        )
    }
}