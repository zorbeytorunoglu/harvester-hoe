package com.zorbeytorunoglu.harvester_hoe.hook.hooks

import com.zorbeytorunoglu.harvester_hoe.hook.Hook
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.Plugin

class VaultHook(private val plugin: Plugin): Hook {

    var economy: Economy? = null

    override fun canHook(): Boolean =
        plugin.server.pluginManager.isPluginEnabled("Vault")

    override fun hook() {
        setupEconomy()
    }

    private fun setupEconomy(): Boolean {
        val rsp = plugin.server.servicesManager.getRegistration(Economy::class.java) ?: return false
        economy = rsp.provider
        return true
    }

}