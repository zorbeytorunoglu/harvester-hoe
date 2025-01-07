package com.zorbeytorunoglu.harvester_hoe

import com.zorbeytorunoglu.harvester_hoe.command.commands.registerMainCommand
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.EnhancementsConfigManager
import com.zorbeytorunoglu.harvester_hoe.configuration.main_config.MainConfigManager
import com.zorbeytorunoglu.harvester_hoe.configuration.messages_config.MessagesConfigManager
import com.zorbeytorunoglu.harvester_hoe.configuration.player_data.PlayerDataManager
import com.zorbeytorunoglu.harvester_hoe.enhancement.EnhancementManager
import com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements.*
import com.zorbeytorunoglu.harvester_hoe.hook.HookManager
import com.zorbeytorunoglu.harvester_hoe.service.Services
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.bukkit.NamespacedKey

object Core {

    internal lateinit var plugin: HarvesterHoe

    lateinit var namespacedKey: NamespacedKey
        private set

    lateinit var mainConfigManager: MainConfigManager
        private set

    lateinit var enhancementsConfigManager: EnhancementsConfigManager
        private set

    lateinit var messagesConfigManager: MessagesConfigManager
        private set

    lateinit var playerDataManager: PlayerDataManager
        private set

    lateinit var enhancementManager: EnhancementManager
        private set

    lateinit var hookManager: HookManager
        private set

    lateinit var services: Services
        private set

    internal fun init(plugin: HarvesterHoe) {
        this.plugin = plugin

        namespacedKey = NamespacedKey(plugin, "harvester_hoe")
        mainConfigManager = MainConfigManager(plugin).also { it.reload() }
        enhancementsConfigManager = EnhancementsConfigManager(plugin).also { it.reload() }
        messagesConfigManager = MessagesConfigManager(plugin).also { it.reload() }
        playerDataManager = PlayerDataManager(plugin).also { it.load() }
        hookManager = HookManager(plugin).also { it.initHook() }

        services = Services(this)

        enhancementManager = EnhancementManager(plugin).also {
            it.registerEnhancements(
                listOf(
                    AutoCollectEnhancement(),
                    AutoSellEnhancement(),
                    BackpackEnhancement(),
                    CoinEnhancement(),
                    FortuneEnhancement(),
                    HasteEnhancement(),
                    KeyFinderEnhancement(),
                    SpeedBoostEnhancement(),
                    TokenChanceEnhancement(),
                ).filter { enhancement -> enhancement.config.enabled == true }
            )
        }

        registerMainCommand(plugin)

    }

}

internal fun initCore(plugin: HarvesterHoe) {
    Core.init(plugin)
}

internal object Scopes {
    val defaultScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    val ioScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val mainScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
}