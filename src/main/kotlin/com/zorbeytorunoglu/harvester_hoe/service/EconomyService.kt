package com.zorbeytorunoglu.harvester_hoe.service

import com.zorbeytorunoglu.harvester_hoe.hook.VaultHook
import org.bukkit.entity.Player

class EconomyService(
    private val vaultHook: VaultHook
) {

    fun depositMoney(player: Player, amount: Double) {
        vaultHook.economy?.depositPlayer(player, amount)
    }

    fun withdrawMoney(player: Player, amount: Double) {
        vaultHook.economy?.withdrawPlayer(player, amount)
    }

    fun getBalance(player: Player): Double = vaultHook.economy?.getBalance(player) ?: 0.0

}