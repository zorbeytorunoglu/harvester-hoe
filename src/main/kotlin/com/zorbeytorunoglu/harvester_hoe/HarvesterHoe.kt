package com.zorbeytorunoglu.harvester_hoe

import com.zorbeytorunoglu.harvester_hoe.enhancement.EnhancementManager
import com.zorbeytorunoglu.harvester_hoe.enhancement.SpeedBoostEnhancement
import org.bukkit.plugin.java.JavaPlugin

class HarvesterHoe: JavaPlugin() {

    override fun onEnable() {
        super.onEnable()

        initCore(this)

        EnhancementManager(this).run {
            registerEnhancement(
                SpeedBoostEnhancement()
            )
        }
    }

    override fun onDisable() {
        super.onDisable()
    }

}