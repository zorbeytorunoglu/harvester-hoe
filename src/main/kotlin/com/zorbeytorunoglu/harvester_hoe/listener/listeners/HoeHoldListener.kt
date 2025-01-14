package com.zorbeytorunoglu.harvester_hoe.listener.listeners

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.event.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.util.isHoldingHoe
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class HoeHoldListener(): Listener {

    private val holdingPlayers = mutableSetOf<String>()

    @EventHandler
    fun onItemHold(event: PlayerMoveEvent) {

        val uuid = event.player.uniqueId.toString()
        val player = event.player

        fun processHold(holding: Boolean) {
            if (holding) {
                if (!holdingPlayers.contains(uuid)) {
                    Core.enhancementManager.dispatchEvent(HoeEvent.OnHold(player))
                    holdingPlayers.add(uuid)
                }
            } else {
                if (holdingPlayers.contains(uuid)) {
                    Core.enhancementManager.dispatchEvent(HoeEvent.OnStoppedHolding(player))
                    holdingPlayers.remove(uuid)
                }
            }
        }

        processHold(event.player.isHoldingHoe())

    }
}