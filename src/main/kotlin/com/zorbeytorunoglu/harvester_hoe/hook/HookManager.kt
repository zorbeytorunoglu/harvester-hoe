package com.zorbeytorunoglu.harvester_hoe.hook

import com.zorbeytorunoglu.harvester_hoe.hook.hooks.PAPIHook
import com.zorbeytorunoglu.harvester_hoe.hook.hooks.VaultHook
import org.bukkit.plugin.Plugin

internal class HookManager(plugin: Plugin) {

    private val hooks = listOf(
        VaultHook(plugin),
        PAPIHook(plugin)
    )

    fun initHooks() {
        hooks.filter { it.canHook() }.forEach { it.hook() }
    }

}