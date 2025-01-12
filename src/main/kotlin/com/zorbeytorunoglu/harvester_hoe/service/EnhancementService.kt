package com.zorbeytorunoglu.harvester_hoe.service

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementTierConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerDataManager
import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerEnhancementConfig
import com.zorbeytorunoglu.harvester_hoe.enhancement.TieredEnhancement
import org.bukkit.entity.Player

class EnhancementService(
    private val playerDataManager: PlayerDataManager
) {

    fun toggleEnhancement(uuid: String, enhancementId: String) {
        val currentData = playerDataManager.getPlayerData(uuid)
        val currentConfig = currentData.enhancements[enhancementId] ?: PlayerEnhancementConfig(enabled = false, tier = 1)
        val updatedUpgrades = currentData.enhancements.toMutableMap()
        updatedUpgrades[enhancementId] = currentConfig.copy(enabled = !currentConfig.enabled)

        playerDataManager.updatePlayerData(uuid, currentData.copy(enhancements = updatedUpgrades))
    }

    fun isEnhancementEnabled(player: Player, enhancementId: String): Boolean {
        return playerDataManager.getPlayerData(player.uniqueId.toString()).enhancements[enhancementId]?.enabled ?: false
    }

    fun getEnhancementLevel(player: Player, enhancementId: String): Int =
        getEnhancementLevel(player.uniqueId.toString(), enhancementId)

    fun getEnhancementLevel(playerUuid: String, enhancementId: String): Int =
        playerDataManager.getPlayerData(playerUuid).enhancements[enhancementId]?.tier ?: 1

    fun getEnhancements(playerUuid: String): Map<String, PlayerEnhancementConfig> =
        playerDataManager.getPlayerData(playerUuid).enhancements

    fun getEnabledEnhancements(playerUuid: String): List<String> = playerDataManager.getPlayerData(playerUuid).enhancements.filter { it.value.enabled }.keys.toList()

    fun removeEnhancement(playerUuid: String, enhancementId: String) {
        val currentData = playerDataManager.getPlayerData(playerUuid)
        val updatedEnhancements = currentData.enhancements.toMutableMap()
        updatedEnhancements.remove(enhancementId)
        playerDataManager.updatePlayerData(playerUuid, currentData.copy(enhancements = updatedEnhancements))
    }

    fun giveEnhancement(playerUuid: String, enhancementId: String, level: Int = 1) {
        val currentData = playerDataManager.getPlayerData(playerUuid)
        val updatedEnhancements = currentData.enhancements.toMutableMap()
        updatedEnhancements[enhancementId] = PlayerEnhancementConfig(enabled = true, tier = level)
        playerDataManager.updatePlayerData(playerUuid, currentData.copy(enhancements = updatedEnhancements))
    }

    fun upgradeEnhancement(playerUuid: String, enhancementId: String) {
        val currentData = playerDataManager.getPlayerData(playerUuid)
        val currentConfig = currentData.enhancements[enhancementId] ?: PlayerEnhancementConfig(enabled = true, tier = 1)
        setEnhancementTier(playerUuid, enhancementId, currentConfig.tier + 1)
    }

    fun downgradeEnhancement(playerUuid: String, enhancementId: String) {
        val currentData = playerDataManager.getPlayerData(playerUuid)
        val currentConfig = currentData.enhancements[enhancementId] ?: PlayerEnhancementConfig(enabled = true, tier = 1)
        setEnhancementTier(playerUuid, enhancementId, currentConfig.tier - 1)
    }

    fun setEnhancementTier(playerUuid: String, enhancementId: String, level: Int) {
        val currentData = playerDataManager.getPlayerData(playerUuid)
        val currentConfig = currentData.enhancements[enhancementId] ?: PlayerEnhancementConfig(enabled = true, tier = 1)
        val updatedUpgrades = currentData.enhancements.toMutableMap()
        updatedUpgrades[enhancementId] = currentConfig.copy(tier = level)
        playerDataManager.updatePlayerData(playerUuid, currentData.copy(enhancements = updatedUpgrades))
    }

    fun hasEnhancement(playerUuid: String, enhancementId: String): Boolean =
        getEnhancements(playerUuid).any { it.key == enhancementId }

    fun isEnhancementEnabled(playerUuid: String, enhancementId: String): Boolean =
        getEnabledEnhancements(playerUuid).any { it == enhancementId }

    fun getMaxEnhancementTierLevel(enhancementId: String): Int? =
        (Core.enhancementManager.getEnhancement(enhancementId) as? TieredEnhancement<*>)?.tiers?.size

    fun <T :EnhancementTierConfig> getPlayerTierConfig(playerUuid: String, enhancementId: String): T? {
        val enhancement = Core.enhancementManager.getEnhancement(enhancementId) ?: return null

        if (enhancement !is TieredEnhancement<*>) return null

        val level = getEnhancementLevel(playerUuid, enhancementId)
        if (level <= 0) return null

        @Suppress("UNCHECKED_CAST")
        return (enhancement.tiers[level] as? T)
    }

}