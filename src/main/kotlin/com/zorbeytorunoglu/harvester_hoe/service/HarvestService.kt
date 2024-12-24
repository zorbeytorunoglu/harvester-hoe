package com.zorbeytorunoglu.harvester_hoe.service

import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerDataManager
import org.bukkit.block.Block
import org.bukkit.entity.Player

class HarvestService(private val playerDataManager: PlayerDataManager) {

    private fun incrementHarvestCount(player: Player, block: Block) {
        val uuid = player.uniqueId.toString()
        val currentData = playerDataManager.getPlayerData(uuid)
        val blockType = block.type.name

        val updatedHarvestMap = currentData.harvestedBlocks.toMutableMap()
        updatedHarvestMap[blockType] = updatedHarvestMap.getOrDefault(blockType, 0) + 1

        playerDataManager.updatePlayerData(uuid, currentData.copy(harvestedBlocks = updatedHarvestMap))
    }

}