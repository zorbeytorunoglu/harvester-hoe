package com.zorbeytorunoglu.harvester_hoe.configuration.main_config

import com.zorbeytorunoglu.harvester_hoe.configuration.Resource
import com.zorbeytorunoglu.harvester_hoe.configuration.ConfigLoader
import com.zorbeytorunoglu.harvester_hoe.util.colorHex
import org.bukkit.Material
import java.util.Locale

class MainConfigLoader: ConfigLoader<MainConfig> {
    override fun load(resource: Resource): MainConfig {
        return MainConfig(
            applyToAllHoes = resource.getBoolean("applyToAllHoes", true),
            harvestBlocks = resource.getStringList("harvest-blocks")
                .mapNotNull { material ->
                    runCatching { Material.valueOf(material.uppercase(Locale.US)) }.getOrNull()
                },
            customHoeConfig = CustomHoeConfig(
                name = resource.getString("custom-hoe.name") ?: "Custom Hoe",
                lore = resource.getStringList("customHoe.lore").map { it.colorHex },
                material = resource.getString("customHoe.material")?.let {
                    Material.valueOf(
                        it.uppercase(
                            Locale.US
                        )
                    )
                } ?: Material.DIAMOND_HOE,
                namespacedKey = resource.getString("customHoe.namespaced-key") ?: "harvester_hoe"
            )
        )
    }
}