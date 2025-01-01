package com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.enhancement.TieredEnhancement
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.SpeedBoostConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.SpeedBoostTier
import com.zorbeytorunoglu.harvester_hoe.event.HoeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

private const val ENHANCEMENT_ID = "speed_boost"

class SpeedBoostEnhancement: TieredEnhancement<SpeedBoostTier, SpeedBoostConfig> {

    override val id: String
        get() = ENHANCEMENT_ID
    override val config: SpeedBoostConfig
        get() = Core.enhancementsConfigManager.get().speedBoostConfig
    override val name: String
        get() = config.name
    override val description: String
        get() = config.description
    override val tiers: Map<Int, SpeedBoostTier>
        get() = config.tiers

    override fun canHandle(event: HoeEvent): Boolean =
        when (event) {
            is HoeEvent.OnHold -> isEnabledForPlayer(event.player)
            is HoeEvent.OnStoppedHolding -> isEnabledForPlayer(event.player)
            else -> false
        }

    override fun handle(event: HoeEvent) {

        when (event) {
            is HoeEvent.OnHold -> {
                val tier = getTier(event.player) ?: return
                event.player.addPotionEffect(
                    PotionEffect(PotionEffectType.SPEED, tier.duration, tier.level)
                )
            }
            is HoeEvent.OnStoppedHolding -> {
                event.player.removePotionEffect(PotionEffectType.SPEED)
            }
            else -> Unit
        }
    }
}