package com.zorbeytorunoglu.harvester_hoe.configuration.player_data

import com.zorbeytorunoglu.harvester_hoe.configuration.DataPersistence
import com.zorbeytorunoglu.harvester_hoe.configuration.Resource

class PlayerDataPersistence: DataPersistence<PlayerData> {
    override fun load(resource: Resource): Map<String, PlayerData> {
        resource.load()

        val data = mutableMapOf<String, PlayerData>()

        for (uuid in resource.getKeys(false)) {
            val harvestedBlocks = resource.getConfigurationSection("$uuid.harvested-blocks")
                ?.getValues(false)
                ?.mapValues { (_, value) -> (value as? Number)?.toInt() ?: 0 }
                ?: mapOf()

            val upgrades = resource.getConfigurationSection("$uuid.enhancements")
                ?.getKeys(false)
                ?.associateWith { enhancementId ->
                    PlayerEnhancementConfig(
                        enabled = resource.getBoolean("$uuid.enhancements.$enhancementId.enabled", false),
                        tier = resource.getInt("$uuid.enhancements.$enhancementId.tier", 1)
                    )
                } ?: mapOf()

            val token = resource.getInt("$uuid.token", 0)

            data[uuid] = PlayerData(
                harvestedBlocks = harvestedBlocks,
                enhancements = upgrades,
                token = token
            )
        }

        return data
    }

    override fun save(resource: Resource, data: Map<String, PlayerData>) {
        data.forEach { (uuid, playerData) ->
            playerData.harvestedBlocks.forEach { (block, count) ->
                resource.set("$uuid.harvested-blocks.$block", count)
            }
            playerData.enhancements.forEach { (enhancementId, config) ->
                resource.set("$uuid.enhancement.$enhancementId.enabled", config.enabled)
                resource.set("$uuid.enhancement.$enhancementId.tier", config.tier)
            }
            playerData.token.let { resource.set("$uuid.token", it) }
        }
        resource.save()
    }
}