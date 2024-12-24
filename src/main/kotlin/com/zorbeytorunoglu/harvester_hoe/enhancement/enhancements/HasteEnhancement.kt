package com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.HasteConfig
import com.zorbeytorunoglu.harvester_hoe.enhancement.Enhancement
import com.zorbeytorunoglu.harvester_hoe.enhancement.HoeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

private const val ENHANCEMENT_ID = "haste"

class HasteEnhancement: Enhancement {

    override val id: String
        get() = ENHANCEMENT_ID
    override val config: HasteConfig
        get() = Core.enhancementsConfigManager.get().hasteConfig
    override val name: String
        get() = config.name
    override val description: String
        get() = config.description

    override fun canHandle(event: HoeEvent): Boolean =
        when (event) {
            is HoeEvent.OnHold -> isEnabledForPlayer(event.player)
            is HoeEvent.OnStoppedHolding -> isEnabledForPlayer(event.player)
            else -> false
        }

    override fun handle(event: HoeEvent) {
        when (event) {
            is HoeEvent.OnHold -> {

                val playerTier = Core.services.enhancementService.getEnhancementLevel(
                    event.player, ENHANCEMENT_ID
                )

                val tierConfig = config.tiers.getOrElse(playerTier) { return }

                event.player.addPotionEffect(
                    PotionEffect(PotionEffectType.FAST_DIGGING, tierConfig.duration, tierConfig.level)
                )
            }
            is HoeEvent.OnStoppedHolding -> {
                event.player.removePotionEffect(PotionEffectType.FAST_DIGGING)
            }
            else -> Unit
        }
    }
}