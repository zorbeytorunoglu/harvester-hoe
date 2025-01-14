package com.zorbeytorunoglu.harvester_hoe.service

import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerData
import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerDataManager
import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerEnhancementConfig
import com.zorbeytorunoglu.harvester_hoe.util.blank

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

    fun getHarvestsInBackpack(uuid: String): Int =
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

    fun updateHarvestsInBackpack(uuid: String, amount: Int) =
        updateData(uuid) { it.copy(harvestsInBackpack = amount) }

    fun addHarvestsToBackpack(uuid: String, amount: Int) =
        updateData(uuid) { it.copy(harvestsInBackpack = it.harvestsInBackpack + amount) }

    fun removeHarvestsToBackpack(uuid: String, amount: Int) =
        updateData(uuid) { it.copy(harvestsInBackpack = it.harvestsInBackpack - amount) }

    fun getXp(uuid: String): Int = playerDataManager.getPlayerData(uuid).xp

    fun addXp(uuid: String, amount: Int) = updateData(uuid) { it.copy(xp = it.xp + amount) }

    fun removeXp(uuid: String, amount: Int) = updateData(uuid) { it.copy(xp = it.xp - amount) }

    fun resetXp(uuid: String) = updateData(uuid) { it.copy(xp = 0) }

    fun setXp(uuid: String, amount: Int) = updateData(uuid) { it.copy(xp = amount) }

    fun setPlayerName(uuid: String, name: String) = updateData(uuid) { it.copy(name = name) }

    fun getPlayerName(uuid: String): String? = playerDataManager.getPlayerData(uuid).name

    fun getTopHarvesters(limit: Int): List<Pair<String, Int>> {
        return playerDataManager.getAllPlayerData()
            .map { (_, data) ->
                val name = data.name ?: blank()
                val totalHarvests = data.harvestedBlocks.values.sum()
                name to totalHarvests
            }
            .sortedByDescending { (_, count) -> count }
            .take(limit)
    }

}