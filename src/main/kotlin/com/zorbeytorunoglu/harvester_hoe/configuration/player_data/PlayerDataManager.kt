package com.zorbeytorunoglu.harvester_hoe.configuration.player_data

import com.zorbeytorunoglu.harvester_hoe.configuration.Resource
import org.bukkit.plugin.Plugin

class PlayerDataManager(plugin: Plugin) {

    private val resource = Resource(plugin, "player_data.yml")
    private val persistence = PlayerDataPersistence()
    private val playerData = mutableMapOf<String, PlayerData>()

    fun load() {
        playerData.clear()
        playerData.putAll(persistence.load(resource))
    }

    fun save() {
        persistence.save(resource, playerData)
    }

    fun getPlayerData(uuid: String): PlayerData = playerData[uuid] ?: PlayerData()

    fun updatePlayerData(uuid: String, updatedData: PlayerData) {
        playerData[uuid] = updatedData
    }

    fun getAllPlayerData(): Map<String, PlayerData> = playerData.toMap()

}