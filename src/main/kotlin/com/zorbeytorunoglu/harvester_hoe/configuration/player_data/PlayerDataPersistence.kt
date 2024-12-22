package com.zorbeytorunoglu.harvester_hoe.configuration.player_data

import com.zorbeytorunoglu.harvester_hoe.configuration.DataPersistence
import com.zorbeytorunoglu.harvester_hoe.configuration.Resource
import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerData

class PlayerDataPersistence: DataPersistence<PlayerData> {
    override fun load(resource: Resource): Map<String, PlayerData> {
        val data = mutableMapOf<String, PlayerData>()

        for (uuid in resource.getKeys(false)) {
            val harvestedBlocks = resource.getConfigurationSection("$uuid.harvestedBlocks")
                ?.getValues(false)
                ?.mapValues { (_, value) -> (value as? Number)?.toInt() ?: 0 }
                ?: mapOf()

            val upgrades = resource.getConfigurationSection("$uuid.upgrades")
                ?.getKeys(false)
                ?.associateWith { upgradeId ->
                    PlayerEnhancementConfig(
                        enabled = resource.getBoolean("$uuid.upgrades.$upgradeId.enabled", false),
                        level = resource.getInt("$uuid.upgrades.$upgradeId.level", 1)
                    )
                } ?: mapOf()

            data[uuid] = PlayerData(harvestedBlocks, upgrades)
        }

        return data
    }

    override fun save(resource: Resource, data: Map<String, PlayerData>) {
        data.forEach { (uuid, playerData) ->
            playerData.harvestedBlocks.forEach { (block, count) ->
                resource.set("$uuid.harvestedBlocks.$block", count)
            }
            playerData.upgrades.forEach { (upgradeId, config) ->
                resource.set("$uuid.upgrades.$upgradeId.enabled", config.enabled)
                resource.set("$uuid.upgrades.$upgradeId.level", config.level)
            }
        }
        resource.save()
    }
}