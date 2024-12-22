package com.zorbeytorunoglu.harvester_hoe

import org.bukkit.plugin.java.JavaPlugin

class HarvesterHoe: JavaPlugin() {

    override fun onEnable() {
        super.onEnable()

        initCore(this)
    }

    override fun onDisable() {
        super.onDisable()
        Core.playerDataManager.save()
    }

}