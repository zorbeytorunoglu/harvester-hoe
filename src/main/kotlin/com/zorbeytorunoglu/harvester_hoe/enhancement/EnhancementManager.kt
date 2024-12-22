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

    fun getEnabledEnhancements(): List<Enhancement> = enhancements.values.filter { it.config.enabled }

    fun getEnhancement(id: String): Enhancement? = enhancements[id]

    fun getEnhancementByName(name: String): Enhancement? = enhancements.values.find { it.name == name }

    fun getEnhancements(): List<Enhancement> = enhancements.values.toList()

    fun registerEnhancement(enhancement: Enhancement) {
        enhancements[enhancement.id] = enhancement
    }

    fun registerEnhancements(enhancements: List<Enhancement>) {
        enhancements.forEach { registerEnhancement(it) }
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