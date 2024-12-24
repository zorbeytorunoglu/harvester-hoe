package com.zorbeytorunoglu.harvester_hoe.configuration.messages_config

import com.zorbeytorunoglu.harvester_hoe.configuration.ConfigManager
import org.bukkit.plugin.Plugin

class MessagesConfigManager(plugin: Plugin): ConfigManager<Messages>(
    plugin = plugin, "messages.yml", MessagesConfigLoader()
)