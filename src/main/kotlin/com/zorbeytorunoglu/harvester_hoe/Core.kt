package com.zorbeytorunoglu.harvester_hoe

import com.zorbeytorunoglu.harvester_hoe.config.Config
import com.zorbeytorunoglu.harvester_hoe.config.EnhancementsConfig
import com.zorbeytorunoglu.harvester_hoe.config.Resource
import com.zorbeytorunoglu.harvester_hoe.config.loadConfig
import com.zorbeytorunoglu.harvester_hoe.config.loadEnhancementsConfig
import org.bukkit.NamespacedKey

object Core {

    private lateinit var plugin: HarvesterHoe

    lateinit var namespacedKey: NamespacedKey
        private set

    lateinit var config: Config
        private set

    lateinit var enhancementsConfig: EnhancementsConfig
        private set

    fun init(plugin: HarvesterHoe) {
        this.plugin = plugin

        namespacedKey = NamespacedKey(plugin, "harvester_hoe")
        config = Resource(plugin, "config.yml").loadConfig()
        enhancementsConfig = Resource(plugin, "enhancements.yml").loadEnhancementsConfig()
    }

}

fun initCore(plugin: HarvesterHoe) {
    Core.init(plugin)
}