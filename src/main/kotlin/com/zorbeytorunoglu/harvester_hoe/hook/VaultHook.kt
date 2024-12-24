package com.zorbeytorunoglu.harvester_hoe.hook

import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.Plugin

class VaultHook(private val plugin: Plugin): Hook {

    var economy: Economy? = null

    override fun hook() {
        if (plugin.server.pluginManager.getPlugin("Vault") == null) return
        setupEconomy()
    }

    private fun setupEconomy(): Boolean {
        val rsp = plugin.server.servicesManager.getRegistration(Economy::class.java) ?: return false
        economy = rsp.provider
        return true
    }

}