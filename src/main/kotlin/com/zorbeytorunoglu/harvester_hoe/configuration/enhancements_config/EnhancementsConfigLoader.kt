package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config

import com.zorbeytorunoglu.harvester_hoe.configuration.ConfigLoader
import com.zorbeytorunoglu.harvester_hoe.configuration.Resource
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.*
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
                tiers = resource.getConfigurationSection("key_finder.tiers")
                    ?.getKeys(false)
                    ?.mapNotNull { tierKey ->
                        tierKey.toIntOrNull()?.let { tierNumber ->
                            tierNumber to KeyFinderTier(
                                chance = resource.getDouble("key_finder.tiers.$tierKey.chance", 0.0),
                                command = resource.getString("key_finder.tiers.$tierKey.command") ?: ""
                            )
                        }
                    }?.toMap() ?: emptyMap()
            ),
            coinConfig = CoinConfig(
                enabled = resource.getBoolean("coin.enabled", true),
                name = (resource.getString("coin.name") ?: "Coin").colorHex,
                description = (resource.getString("coin.description") ?: "Gives you coins!").colorHex,
                messageEnabled = resource.getBoolean("coin.message-enabled", true),
                message = (resource.getString("coin.message") ?: "&aYou found a coin!").colorHex,
                tiers = resource.getConfigurationSection("coin.tiers")
                    ?.getKeys(false)
                    ?.mapNotNull { tierKey ->
                        tierKey.toIntOrNull()?.let { tierNumber ->
                            tierNumber to CoinTier(
                                chance = resource.getDouble("coin.tiers.$tierKey.chance", 0.0),
                                command = resource.getString("coin.tiers.$tierKey.command") ?: ""
                            )
                        }
                    }?.toMap() ?: emptyMap()
            ),
            fortuneConfig = FortuneConfig(
                enabled = resource.getBoolean("fortune.enabled", true),
                name = (resource.getString("fortune.name") ?: "Fortune").colorHex,
                description = (resource.getString("fortune.description") ?: "Gives you extra canes!").colorHex,
                messageEnabled = resource.getBoolean("fortune.message-enabled", true),
                message = (resource.getString("fortune.message") ?: "&aYou got extra %amount% canes because of fortune!").colorHex,
                tiers = resource.getConfigurationSection("fortune.tiers")
                    ?.getKeys(false)
                    ?.mapNotNull { tierKey ->
                        tierKey.toIntOrNull()?.let { tierNumber ->
                            tierNumber to FortuneTier(
                                chance = resource.getDouble("fortune.tiers.$tierKey.chance", 0.0),
                                amount = resource.getInt("fortune.tiers.$tierKey.amount")
                            )
                        }
                    }?.toMap() ?: emptyMap()
            ),
            excavatorConfig = ExcavatorConfig(
                enabled = resource.getBoolean("excavator.enabled", true),
                name = (resource.getString("excavator.name") ?: "Excavator").colorHex,
                description = (resource.getString("excavator.description") ?: "Helps you to farm canes!").colorHex,
                tiers = resource.getConfigurationSection("excavator.tiers")
                    ?.getKeys(false)
                    ?.mapNotNull { tierKey ->
                        tierKey.toIntOrNull()?.let { tierNumber ->
                            tierNumber to ExcavatorTier(
                                radius = resource.getInt("excavator.tiers.$tierKey.radius", 3),
                                chance = resource.getDouble("excavator.tiers.$tierKey.chance", 0.05),
                            )
                        }
                    }?.toMap() ?: emptyMap()
            )
        )
    }
}