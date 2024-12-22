package com.zorbeytorunoglu.harvester_hoe.enhancement

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.HasteConfig
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
                val level = config.level
                val duration = config.duration

                event.player.addPotionEffect(
                    PotionEffect(PotionEffectType.FAST_DIGGING, duration, level)
                )
            }
            is HoeEvent.OnStoppedHolding -> {
                event.player.removePotionEffect(PotionEffectType.FAST_DIGGING)
            }
            else -> Unit
        }
    }
}