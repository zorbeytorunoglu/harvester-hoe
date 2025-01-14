package com.zorbeytorunoglu.harvester_hoe.hook

import com.zorbeytorunoglu.harvester_hoe.hook.hooks.PAPIHook
import com.zorbeytorunoglu.harvester_hoe.hook.hooks.VaultHook
import org.bukkit.plugin.Plugin

internal class HookManager(plugin: Plugin) {

    val vaultHook: VaultHook = VaultHook(plugin)
    val papiHook = PAPIHook(plugin)

    fun initHooks() {
        vaultHook.run { if (canHook()) hook() }
        papiHook.run { if (canHook()) hook() }
    }

}