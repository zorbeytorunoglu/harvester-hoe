package com.zorbeytorunoglu.harvester_hoe.enhancement

import com.zorbeytorunoglu.harvester_hoe.HarvesterHoe
import com.zorbeytorunoglu.harvester_hoe.event.listener.HarvestListener
import com.zorbeytorunoglu.harvester_hoe.event.listener.HoldingListener
import org.bukkit.event.Listener

class EnhancementManager(
    private val plugin: HarvesterHoe
) {

    private val enhancements = mutableMapOf<String, Enhancement>()

    init {
        registerEvents()
    }

    private fun registerEvents() {
        plugin.registerEvents(
            HarvestListener(this),
            HoldingListener(this)
        )
    }

    fun registerEnhancement(enhancement: Enhancement) {
        enhancements[enhancement.id] = enhancement
    }

    fun dispatchEvent(event: HoeEvent) {
        enhancements.values
            .filter { it.canHandle(event) }
            .forEach { it.handle(event) }
    }

}

private fun HarvesterHoe.registerEvents(
    vararg events: Listener
) {
    events.forEach {
        server.pluginManager.registerEvents(it, this)
    }
}