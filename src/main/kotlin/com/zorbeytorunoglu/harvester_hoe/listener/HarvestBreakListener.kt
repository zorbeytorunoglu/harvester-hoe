package com.zorbeytorunoglu.harvester_hoe.listener

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.enhancement.EnhancementManager
import com.zorbeytorunoglu.harvester_hoe.event.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.util.isHarvestMaterial
import com.zorbeytorunoglu.harvester_hoe.util.isHoldingHoe
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class HarvestBreakListener(
    private val enhancementManager: EnhancementManager
): Listener {

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.player.isHoldingHoe() && event.block.isHarvestMaterial()) {

            enhancementManager.dispatchEvent(HoeEvent.OnHarvest(event.player, event.block, event))

            Core.services.playerDataService.addHarvestedBlock(
                uuid = event.player.uniqueId.toString(),
                blockType = event.block.type.name,
                amount = 1
            )

        }
    }

}