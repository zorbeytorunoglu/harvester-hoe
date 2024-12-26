package com.zorbeytorunoglu.harvester_hoe.service

import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerData
import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerDataManager
import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerEnhancementConfig

class PlayerDataService(
    private val playerDataManager: PlayerDataManager
) {

    fun getHarvestedBlocks(uuid: String): Map<String, Int> =
        playerDataManager.getPlayerData(uuid).harvestedBlocks

    fun getTokens(uuid: String): Int =
        playerDataManager.getPlayerData(uuid).token

    fun getEnhancements(uuid: String): Map<String, PlayerEnhancementConfig> =
        playerDataManager.getPlayerData(uuid).enhancements

    fun getEnabledEnhancements(uuid: String): List<String> =
        playerDataManager.getPlayerData(uuid).enhancements.filter { it.value.enabled }.keys.toList()

    fun getHarvestsInBackpack(uuid: String): Double =
        playerDataManager.getPlayerData(uuid).harvestsInBackpack

    private fun updateData(uuid: String, transform: (PlayerData) -> PlayerData) {
        val currentData = playerDataManager.getPlayerData(uuid)
        playerDataManager.updatePlayerData(uuid, transform(currentData))
    }

    fun updateHarvestedBlocks(uuid: String, blocks: Map<String, Int>) =
        updateData(uuid) { it.copy(harvestedBlocks = blocks) }

    fun addHarvestedBlock(uuid: String, blockType: String, amount: Int = 1) =
        updateData(uuid) {
            val currentAmount = it.harvestedBlocks[blockType] ?: 0
            it.copy(harvestedBlocks = it.harvestedBlocks + (blockType to currentAmount + amount))
        }

    fun updateTokens(uuid: String, tokens: Int) =
        updateData(uuid) { it.copy(token = tokens) }

    fun addTokens(uuid: String, amount: Int) =
        updateData(uuid) { it.copy(token = it.token + amount) }

    fun updateEnhancements(uuid: String, enhancements: Map<String, PlayerEnhancementConfig>) =
        updateData(uuid) { it.copy(enhancements = enhancements) }

    fun updateEnhancement(uuid: String, enhancementId: String, config: PlayerEnhancementConfig) =
        updateData(uuid) {
            it.copy(enhancements = it.enhancements + (enhancementId to config))
        }

    fun setEnhancementEnabled(uuid: String, enhancementId: String, enabled: Boolean) =
        updateData(uuid) { data ->
            val updatedEnhancements = data.enhancements.mapValues { (key, config) ->
                if (key == enhancementId) config.copy(enabled = enabled) else config
            }
            data.copy(enhancements = updatedEnhancements)
        }

    fun updateHarvestsInBackpack(uuid: String, amount: Double) =
        updateData(uuid) { it.copy(harvestsInBackpack = amount) }

    fun addHarvestsToBackpack(uuid: String, amount: Int) =
        updateData(uuid) { it.copy(harvestsInBackpack = it.harvestsInBackpack + amount) }

}