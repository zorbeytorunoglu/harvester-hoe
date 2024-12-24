package com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.AutoSellConfig
import com.zorbeytorunoglu.harvester_hoe.enhancement.Enhancement
import com.zorbeytorunoglu.harvester_hoe.enhancement.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.enhancement.with
import org.bukkit.inventory.ItemStack

private const val ENHANCEMENT_ID = "auto_sell"

class AutoSellEnhancement: Enhancement {

    override val id: String = ENHANCEMENT_ID
    override val config: AutoSellConfig = Core.enhancementsConfigManager.get().autoSellConfig
    override val name: String = config.name
    override val description: String = config.description

    override fun canHandle(event: HoeEvent): Boolean = when (event) {
        is HoeEvent.OnHarvestCollected -> isEnabledForPlayer(event.player)
        else -> false
    }

    override fun handle(event: HoeEvent) = (event as HoeEvent.OnHarvestCollected).with {
        val blockValue = config.priceList.getOrElse(event.type) { return@with }

        ItemStack(event.type, event.amount).let { player.inventory.removeItem(it) }

        Core.services.economyService.depositMoney(event.player, blockValue * event.amount)
        event.player.sendMessage("bloklarin satildi, sana bu kadar para: ${event.amount}")
    }
}