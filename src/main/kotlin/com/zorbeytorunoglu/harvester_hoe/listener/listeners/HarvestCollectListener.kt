package com.zorbeytorunoglu.harvester_hoe.listener.listeners

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.event.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.util.isHarvestMaterial
import com.zorbeytorunoglu.harvester_hoe.util.isHoldingHoe
import com.zorbeytorunoglu.harvester_hoe.util.runTaskLater
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent

class HarvestCollectListener(): Listener {

    @EventHandler
    fun onEntityPickupItemEvent(event: EntityPickupItemEvent) {
        val player = event.entity as? Player ?: return
        val itemStack = event.item.itemStack

        if (itemStack.isHarvestMaterial() && player.isHoldingHoe()) {
            runTaskLater(1L) {
                Core.enhancementManager.dispatchEvent(
                    HoeEvent.OnHarvestCollected(
                        player = player,
                        type = itemStack.type,
                        amount = itemStack.amount
                    )
                )
            }
        }
    }

}