package com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.enhancement.TieredEnhancement
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.CoinConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.CoinTier
import com.zorbeytorunoglu.harvester_hoe.event.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.enhancement.with
import com.zorbeytorunoglu.harvester_hoe.util.replacePlayerName
import org.bukkit.Bukkit
import kotlin.random.Random

const val COIN_ENHANCEMENT_ID = "coin"

class CoinEnhancement: TieredEnhancement<CoinTier> {

    override val id: String = COIN_ENHANCEMENT_ID
    override val config: CoinConfig = Core.enhancementsConfigManager.get().coinConfig
    override val name: String = config.name
    override val description: String = config.description
    override val tiers: Map<Int, CoinTier> = config.tiers

    override fun canHandle(event: HoeEvent): Boolean = when (event) {
        is HoeEvent.OnHarvest -> isEnabledForPlayer(event.player)
        else -> false
    }

    override fun handle(event: HoeEvent) = (event as HoeEvent.OnHarvest).with {
        val tier = getTier(player) ?: return@with

        val chance = tier.chance
        val randomValue = Random.nextDouble(0.0, 1.0)

        if (randomValue < chance) {
            Bukkit.getServer().dispatchCommand(
                Bukkit.getConsoleSender(),
                tier.command.replacePlayerName(event.player.name)
            )
            if (config.messageEnabled)
                player.sendMessage(config.message)
        }
    }

}