package com.zorbeytorunoglu.harvester_hoe.service

import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerDataManager
import org.bukkit.entity.Player

class TokenService(
    private val playerDataManager: PlayerDataManager
) {

    fun addTokens(uuid: String, amount: Int) {
        val currentData = playerDataManager.getPlayerData(uuid)
        val updatedTokens = currentData.token + amount
        setTokens(uuid, updatedTokens)
    }

    fun setTokens(uuid: String, amount: Int) {
        val currentData = playerDataManager.getPlayerData(uuid)
        playerDataManager.updatePlayerData(uuid, currentData.copy(token = amount))
    }

    fun removeTokens(uuid: String, amount: Int) {
        val currentData = playerDataManager.getPlayerData(uuid)
        val updatedTokens = currentData.token - amount
        setTokens(uuid, updatedTokens)
    }

    fun hasTokens(player: Player, amount: Int): Boolean {
        return getTokens(player) >= amount
    }

    fun takeTokens(player: Player, amount: Int) {
        if (hasTokens(player, amount)) {
            removeTokens(player.uniqueId.toString(), amount)
        }
    }

    fun giveTokens(player: Player, amount: Int) {
        addTokens(player.uniqueId.toString(), amount)
    }

    fun resetTokens(player: Player) {
        setTokens(player.uniqueId.toString(), 0)
    }

    fun getTokens(player: Player): Int {
        return playerDataManager.getPlayerData(player.uniqueId.toString()).token
    }

}