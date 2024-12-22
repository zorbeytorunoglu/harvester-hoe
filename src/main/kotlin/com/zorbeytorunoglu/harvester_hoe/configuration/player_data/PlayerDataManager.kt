package com.zorbeytorunoglu.harvester_hoe.configuration.player_data

import com.zorbeytorunoglu.harvester_hoe.configuration.Resource
import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerData
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class PlayerDataManager(
    private val plugin: Plugin
) {

    private val resource = Resource(plugin, "playerData.yml")
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

    fun toggleEnhancement(player: Player, enhancementId: String) {
        val uuid = player.uniqueId.toString()
        val currentData = getPlayerData(uuid)

        val currentConfig = currentData.upgrades[enhancementId] ?: PlayerEnhancementConfig(enabled = false, level = 1)
        val updatedUpgrades = currentData.upgrades.toMutableMap()
        updatedUpgrades[enhancementId] = currentConfig.copy(enabled = !currentConfig.enabled)

        playerData[uuid] = currentData.copy(upgrades = updatedUpgrades)
    }

    fun isEnhancementEnabled(player: Player, enhancementId: String): Boolean {
        return getPlayerData(player.uniqueId.toString()).upgrades[enhancementId]?.enabled ?: false
    }

    fun getEnhancementLevel(player: Player, enhancementId: String): Int {
        return getPlayerData(player.uniqueId.toString()).upgrades[enhancementId]?.level ?: 1
    }

    private fun incrementHarvestCount(player: Player, block: Block) {
        val uuid = player.uniqueId.toString()
        val currentData = getPlayerData(uuid)
        val blockType = block.type.name

        val updatedHarvestMap = currentData.harvestedBlocks.toMutableMap()
        updatedHarvestMap[blockType] = updatedHarvestMap.getOrDefault(blockType, 0) + 1

        playerData[uuid] = currentData.copy(
            harvestedBlocks = updatedHarvestMap
        )
    }

}