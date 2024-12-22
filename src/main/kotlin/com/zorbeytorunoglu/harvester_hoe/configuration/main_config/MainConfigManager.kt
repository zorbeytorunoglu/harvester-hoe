package com.zorbeytorunoglu.harvester_hoe.configuration.main_config

import com.zorbeytorunoglu.harvester_hoe.configuration.ConfigManager
import org.bukkit.plugin.Plugin

class MainConfigManager(plugin: Plugin): ConfigManager<MainConfig>(
    plugin = plugin, "config.yml", MainConfigLoader()
)