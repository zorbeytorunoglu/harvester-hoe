package com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.TieredEnhancement
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.KeyFinderConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.KeyFinderTier
import com.zorbeytorunoglu.harvester_hoe.enhancement.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.enhancement.with
import com.zorbeytorunoglu.harvester_hoe.util.replacePlayerName
import org.bukkit.Bukkit
import kotlin.random.Random

private const val ENHANCEMENT_ID = "key_finder"

class KeyFinderEnhancement: TieredEnhancement<KeyFinderTier, KeyFinderConfig> {

    override val id: String = ENHANCEMENT_ID
    override val config: KeyFinderConfig = Core.enhancementsConfigManager.get().keyFinderConfig
    override val name: String = config.name
    override val description: String = config.description
    override val tiers: Map<Int, KeyFinderTier> = config.tiers

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
