package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config

import com.zorbeytorunoglu.harvester_hoe.configuration.Resource
import com.zorbeytorunoglu.harvester_hoe.configuration.ConfigLoader
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.AutoCollectConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.AutoSellConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.BackpackConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.BackpackTier
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.HasteConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.HasteTier
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.KeyFinderConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.KeyFinderTier
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
                name = (resource.getString("speed_boost.name") ?: "Speed Boost").colorHex,
                description = (resource.getString("speed_boost.description") ?: "Gives you speed boost!").colorHex,
                enabled = resource.getBoolean("speed_boost.enabled", true),
                tiers = resource.getConfigurationSection("speed_boost.tiers")
                    ?.getKeys(false)
                    ?.mapNotNull { tierKey ->
                        tierKey.toIntOrNull()?.let { tierNumber ->
                            tierNumber to SpeedBoostTier(
                                duration = resource.getString("speed_boost.tiers.$tierKey.duration")
                                    ?.takeIf { it != "MAX" }
                                    ?.toIntOrNull()
                                    ?: Int.MAX_VALUE,
                                level = resource.getInt("speed_boost.tiers.$tierKey.level", 1)
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
                enabled = resource.getBoolean("auto_collect.enabled", true),
                name = (resource.getString("auto_collect.name") ?: "Auto Collect").colorHex,
                description = (resource.getString("auto_collect.description") ?: "Automatically collects harvests!").colorHex
            ),
            tokenChanceConfig = TokenChanceConfig(
                enabled = resource.getBoolean("token_chance.enabled", true),
                name = (resource.getString("token_chance.name") ?: "Token Chance").colorHex,
                description = (resource.getString("token_chance.description") ?: "Gives you a chance to get tokens!").colorHex,
                messageEnabled = resource.getBoolean("token_chance.message-enabled", true),
                message = (resource.getString("token_chance.message") ?: "&aYou found a token!").colorHex,
                tiers = resource.getConfigurationSection("token_chance.tiers")
                    ?.getKeys(false)
                    ?.mapNotNull { tierKey ->
                        tierKey.toIntOrNull()?.let { tierNumber ->
                            tierNumber to TokenChanceTier(
                                chance = resource.getDouble("token_chance.tiers.$tierKey.chance", 0.0)
                            )
                        }
                    }?.toMap() ?: emptyMap()
            ),
            autoSellConfig = AutoSellConfig(
                enabled = resource.getBoolean("auto_sell.enabled", true),
                name = (resource.getString("auto_sell.name") ?: "Auto Sell").colorHex,
                description = (resource.getString("auto_sell.description") ?: "Automatically sells items!").colorHex,
                priceList = resource.getConfigurationSection("auto_sell.price-list")?.getKeys(false)
                    ?.mapNotNull { Material.valueOf(it) }
                    ?.associateWith { resource.getDouble("auto_sell.price-list.$it", 0.0) } ?: emptyMap()
            ),
            enhancementTierCount = resource.getKeys(false).associateWith { enhId ->
                resource.getConfigurationSection("$enhId.tiers")?.getKeys(false)?.size ?: 0
            },
            backpackConfig = BackpackConfig(
                enabled = resource.getBoolean("backpack.enabled", true),
                name = (resource.getString("backpack.name") ?: "Auto Sell").colorHex,
                description = (resource.getString("backpack.description") ?: "Automatically sells items!").colorHex,
                tiers = resource.getConfigurationSection("token_chance.tiers")
                    ?.getKeys(false)
                    ?.mapNotNull { tierKey ->
                        tierKey.toIntOrNull()?.let { tierNumber ->
                            tierNumber to BackpackTier(
                                size = resource.getString("backpack.tiers.$tierKey.size")
                                    ?.takeIf { it != "MAX" }
                                    ?.toIntOrNull()
                                    ?: Int.MAX_VALUE
                            )
                        }
                    }?.toMap() ?: emptyMap(),
                messageEnabled = resource.getBoolean("backpack.message-enabled", true),
                message = (resource.getString("backpack.message") ?: "&aYour backpack is full!").colorHex
            ),
            keyFinderConfig = KeyFinderConfig(
                enabled = resource.getBoolean("key_finder.enabled", true),
                name = (resource.getString("key_finder.name") ?: "Key Finder").colorHex,
                description = (resource.getString("key_finder.description") ?: "Finds keys!").colorHex,
                messageEnabled = resource.getBoolean("key_finder.message-enabled", true),
                message = (resource.getString("key_finder.message") ?: "&aYou found a key!").colorHex,
                tiers = resource.getConfigurationSection("token_chance.tiers")
                    ?.getKeys(false)
                    ?.mapNotNull { tierKey ->
                        tierKey.toIntOrNull()?.let { tierNumber ->
                            tierNumber to KeyFinderTier(
                                chance = resource.getDouble("key_finder.tiers.$tierKey.chance", 0.0),
                                command = resource.getString("key_finder.tiers.$tierKey.command") ?: ""
                            )
                        }
                    }?.toMap() ?: emptyMap()
            )
        )
    }
}