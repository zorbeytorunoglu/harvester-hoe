package com.zorbeytorunoglu.harvester_hoe.listener

import com.zorbeytorunoglu.harvester_hoe.enhancement.EnhancementManager
import com.zorbeytorunoglu.harvester_hoe.event.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.util.isHoldingHoe
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class HoeHoldListener(
    private val enhancementManager: EnhancementManager
): Listener {

    private val holdingPlayers = mutableSetOf<String>()

    @EventHandler
    fun onItemHold(event: PlayerMoveEvent) {

        val uuid = event.player.uniqueId.toString()
        val player = event.player

        fun processHold(holding: Boolean) {
            if (holding) {
                if (!holdingPlayers.contains(uuid)) {
                    enhancementManager.dispatchEvent(HoeEvent.OnHold(player))
                    holdingPlayers.add(uuid)
                }
            } else {
                if (holdingPlayers.contains(uuid)) {
                    enhancementManager.dispatchEvent(HoeEvent.OnStoppedHolding(player))
                    holdingPlayers.remove(uuid)
                }
            }
        }

        processHold(event.player.isHoldingHoe())

    }
}