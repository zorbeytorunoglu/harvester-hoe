package com.zorbeytorunoglu.harvester_hoe.listener

import com.zorbeytorunoglu.harvester_hoe.HarvesterHoe
import com.zorbeytorunoglu.harvester_hoe.listener.listeners.HarvestBreakListener
import com.zorbeytorunoglu.harvester_hoe.listener.listeners.HarvestCollectListener
import com.zorbeytorunoglu.harvester_hoe.listener.listeners.HoeHoldListener
import com.zorbeytorunoglu.harvester_hoe.listener.listeners.PlayerJoinListener
import org.bukkit.event.Listener

internal class ListenerManager(
    private val plugin: HarvesterHoe
) {

    fun registerListeners() {
        plugin.registerListeners(
            HarvestBreakListener(),
            HoeHoldListener(),
            HarvestCollectListener(),
            PlayerJoinListener(),
        )
    }

}

private fun HarvesterHoe.registerListeners(
    vararg events: Listener
) {
    events.forEach {
        server.pluginManager.registerEvents(it, this)
    }
}