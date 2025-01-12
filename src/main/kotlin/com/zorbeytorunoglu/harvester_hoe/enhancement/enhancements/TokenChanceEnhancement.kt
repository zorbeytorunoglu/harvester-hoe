package com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.enhancement.TieredEnhancement
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.TokenChanceConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.TokenChanceTier
import com.zorbeytorunoglu.harvester_hoe.event.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.enhancement.with
import com.zorbeytorunoglu.harvester_hoe.util.isLucky

const val TOKEN_CHANCE_ENHANCEMENT_ID = "token_chance"

class TokenChanceEnhancement: TieredEnhancement<TokenChanceTier> {

    override val id: String = TOKEN_CHANCE_ENHANCEMENT_ID
    override val config: TokenChanceConfig = Core.enhancementsConfigManager.get().tokenChanceConfig
    override val name: String = config.name
    override val description: String = config.description
    override val tiers: Map<Int, TokenChanceTier> = config.tiers

    override fun canHandle(event: HoeEvent): Boolean = when (event) {
        is HoeEvent.OnHarvest -> {
            isEnabledForPlayer(event.player)
        }
        else -> false
    }

    override fun handle(event: HoeEvent) = (event as HoeEvent.OnHarvest).with {
        if (event.event.isCancelled) return@with

        val tier = getTier(event.player) ?: return@with

        if (!event.player.isLucky(tier.chance)) return@with

        Core.services.tokenService.giveTokens(event.player, 1)
        if (config.messageEnabled) player.sendMessage(config.message)
    }

}