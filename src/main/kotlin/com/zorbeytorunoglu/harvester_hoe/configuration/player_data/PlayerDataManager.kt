package com.zorbeytorunoglu.harvester_hoe.configuration.player_data

import com.zorbeytorunoglu.harvester_hoe.configuration.Resource
import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerData
import com.zorbeytorunoglu.harvester_hoe.service.EnhancementService
import com.zorbeytorunoglu.harvester_hoe.service.HarvestService
import com.zorbeytorunoglu.harvester_hoe.service.TokenService
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class PlayerDataManager(plugin: Plugin) {

    private val resource = Resource(plugin, "player_data.yml")
    private val persistence = PlayerDataPersistence()
    private val playerData = mutableMapOf<String, PlayerData>()

    val enhancementService = EnhancementService(this)
    val tokenService = TokenService(this)
    val harvestService = HarvestService(this)

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

}