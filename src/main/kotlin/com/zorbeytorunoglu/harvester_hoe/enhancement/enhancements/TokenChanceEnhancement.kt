package com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.TokenChanceConfig
import com.zorbeytorunoglu.harvester_hoe.enhancement.Enhancement
import com.zorbeytorunoglu.harvester_hoe.enhancement.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.enhancement.with
import kotlin.random.Random

private const val ENHANCEMENT_ID = "token_chance"

class TokenChanceEnhancement: Enhancement {

    override val id: String = ENHANCEMENT_ID
    override val config: TokenChanceConfig = Core.enhancementsConfigManager.get().tokenChanceConfig
    override val name: String = config.name
    override val description: String = config.description

    override fun canHandle(event: HoeEvent): Boolean = when (event) {
        is HoeEvent.OnHarvest -> {
            isEnabledForPlayer(event.player)
        }
        else -> false
    }

    override fun handle(event: HoeEvent) = (event as HoeEvent.OnHarvest).with {
        if (event.event.isCancelled) return@with

        val playerTier = Core.services.enhancementService.getEnhancementLevel(event.player, ENHANCEMENT_ID)

        val tierConfig = config.tiers.getOrElse(playerTier) { return }

        val chance = tierConfig.chance
        val randomValue = Random.nextDouble(0.0, 1.0)

        if (randomValue < chance) {
            Core.playerDataManager.tokenService.giveTokens(event.player, 1)
            if (config.messageEnabled)
                player.sendMessage(config.message)
        }
    }
}