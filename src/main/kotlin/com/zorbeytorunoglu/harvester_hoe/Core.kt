package com.zorbeytorunoglu.harvester_hoe

import com.zorbeytorunoglu.harvester_hoe.command.commands.HarvesterHoeCommand
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementsConfigManager
import com.zorbeytorunoglu.harvester_hoe.configuration.main_config.MainConfigManager
import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerDataManager
import com.zorbeytorunoglu.harvester_hoe.enhancement.EnhancementManager
import com.zorbeytorunoglu.harvester_hoe.enhancement.HasteEnhancement
import com.zorbeytorunoglu.harvester_hoe.enhancement.SpeedBoostEnhancement
import org.bukkit.NamespacedKey
import org.bukkit.command.CommandExecutor
import org.bukkit.command.TabCompleter

object Core {

    private lateinit var plugin: HarvesterHoe

    lateinit var namespacedKey: NamespacedKey
        private set

    lateinit var mainConfigManager: MainConfigManager
        private set

    lateinit var enhancementsConfigManager: EnhancementsConfigManager
        private set

    lateinit var playerDataManager: PlayerDataManager
        private set

    lateinit var enhancementManager: EnhancementManager
        private set

    internal fun init(plugin: HarvesterHoe) {
        this.plugin = plugin

        namespacedKey = NamespacedKey(plugin, "harvester_hoe")
        mainConfigManager = MainConfigManager(plugin).also { it.reload() }
        enhancementsConfigManager = EnhancementsConfigManager(plugin).also { it.reload() }
        playerDataManager = PlayerDataManager(plugin).also { it.load() }

        enhancementManager = EnhancementManager(plugin).also {
            it.registerEnhancements(
                listOf(
                    SpeedBoostEnhancement(),
                    HasteEnhancement()
                ).filter { enhancement -> enhancement.config.enabled == true }
            )
        }

        plugin.registerCommand()

    }

    private fun HarvesterHoe.registerCommand() {

        val mainCommand = HarvesterHoeCommand()

        getCommand("harvesterhoe")?.apply {
            setExecutor(CommandExecutor { sender, command, label, args ->
                mainCommand.handleExecute(sender, args)
                true
            })
            tabCompleter = TabCompleter { sender, command, label, args ->
                mainCommand.handleTabCompletion(sender, args)
            }
        }
    }

}

internal fun initCore(plugin: HarvesterHoe) {
    Core.init(plugin)
}