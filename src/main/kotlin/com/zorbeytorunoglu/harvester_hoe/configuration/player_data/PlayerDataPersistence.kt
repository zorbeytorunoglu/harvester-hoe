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

            data[uuid] = PlayerData(
                harvestedBlocks = harvestedBlocks,
                enhancements = upgrades,
                harvestsInBackpack = resource.getInt("$uuid.harvests-in-backpack", 0),
                token = resource.getInt("$uuid.token", 0),
                xp = resource.getInt("$uuid.xp", 0)
            )
        }

        return data
    }

    override fun save(resource: Resource, data: Map<String, PlayerData>) {
        data.forEach { (uuid, playerData) ->

            playerData.run {
                harvestedBlocks.forEach { (block, count) ->
                    resource.set("$uuid.harvested-blocks.$block", count)
                }
                enhancements.forEach { (enhancementId, config) ->
                    resource.set("$uuid.enhancements.$enhancementId.enabled", config.enabled)
                    resource.set("$uuid.enhancements.$enhancementId.tier", config.tier)
                }
                resource.set("$uuid.harvests-in-backpack", harvestsInBackpack)
                resource.set("$uuid.token", token)
                resource.set("$uuid.xp", xp)
            }

        }
        resource.save()
    }
}