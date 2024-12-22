package com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config

import com.zorbeytorunoglu.harvester_hoe.configuration.ConfigManager
import org.bukkit.plugin.Plugin

class EnhancementsConfigManager(plugin: Plugin): ConfigManager<EnhancementsConfig>(
    plugin = plugin, "enhancements.yml", EnhancementsConfigLoader()
)