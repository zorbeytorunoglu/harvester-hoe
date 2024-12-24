package com.zorbeytorunoglu.harvester_hoe.hook

import org.bukkit.plugin.Plugin

class HookManager(plugin: Plugin) {

    val vaultHook = VaultHook(plugin)

    fun initHook() {
        vaultHook.hook()
    }

}