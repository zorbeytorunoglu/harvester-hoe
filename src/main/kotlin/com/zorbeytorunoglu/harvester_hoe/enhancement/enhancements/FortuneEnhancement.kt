package com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.enhancement.TieredEnhancement
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.FortuneConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.FortuneTier
import com.zorbeytorunoglu.harvester_hoe.event.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.enhancement.with
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

const val FORTUNE_ENHANCEMENT_ID = "fortune"

class FortuneEnhancement: TieredEnhancement<FortuneTier> {

    override val id: String = FORTUNE_ENHANCEMENT_ID
    override val config: FortuneConfig = Core.enhancementsConfigManager.get().fortuneConfig
    override val name: String = config.name
    override val description: String = config.description
    override val tiers: Map<Int, FortuneTier> = config.tiers

    override fun canHandle(event: HoeEvent): Boolean = when (event) {
        is HoeEvent.OnInventoryStore -> isEnabledForPlayer(event.player)
        else -> false
    }

    override fun handle(event: HoeEvent) = (event as HoeEvent.OnInventoryStore).with {
        val tier = getTier(player) ?: return@with

        val chance = tier.chance
        val randomValue = Random.nextDouble(0.0, 1.0)

        if (randomValue < chance) {
            val extraAmount = tier.amount
            event.player.inventory.addItem(ItemStack(event.type, extraAmount))
            if (config.messageEnabled)
                player.sendMessage(config.message)
        }
    }

}