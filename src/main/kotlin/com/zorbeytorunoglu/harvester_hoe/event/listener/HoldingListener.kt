package com.zorbeytorunoglu.harvester_hoe.event.listener

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.enhancement.EnhancementManager
import com.zorbeytorunoglu.harvester_hoe.enhancement.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.util.isCustomHoe
import com.zorbeytorunoglu.harvester_hoe.util.isHoe
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class HoldingListener(
    private val enhancementManager: EnhancementManager
): Listener {

    private val holdingPlayers = mutableSetOf<String>()

    @EventHandler
    fun onItemHold(event: PlayerMoveEvent) {

        val uuid = event.player.uniqueId.toString()
        val player = event.player
        val item = player.inventory.itemInMainHand

        val allHoesApply = Core.config.applyToAllHoes

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

        processHold(
            if (allHoesApply) item.isHoe() else item.isCustomHoe()
        )

    }
}