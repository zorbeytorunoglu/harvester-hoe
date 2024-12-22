package com.zorbeytorunoglu.harvester_hoe.event.listener

import com.zorbeytorunoglu.harvester_hoe.enhancement.EnhancementManager
import com.zorbeytorunoglu.harvester_hoe.enhancement.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.util.isHarvestBlock
import com.zorbeytorunoglu.harvester_hoe.util.isHoldingHoe
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class HarvestListener(
    private val enhancementManager: EnhancementManager
): Listener {

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.player.isHoldingHoe() && event.block.isHarvestBlock())
            enhancementManager.dispatchEvent(HoeEvent.OnHarvest(event.player, event.block))
    }

}