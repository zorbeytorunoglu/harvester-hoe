package com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.enhancement.TieredEnhancement
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.HasteConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.HasteTier
import com.zorbeytorunoglu.harvester_hoe.event.HoeEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

const val HASTE_ENHANCEMENT_ID = "haste"

class HasteEnhancement: TieredEnhancement<HasteTier> {

    override val id: String
        get() = HASTE_ENHANCEMENT_ID
    override val config: HasteConfig
        get() = Core.enhancementsConfigManager.get().hasteConfig
    override val name: String
        get() = config.name
    override val description: String
        get() = config.description
    override val tiers: Map<Int, HasteTier>
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
                    PotionEffect(PotionEffectType.FAST_DIGGING, tier.duration, tier.level)
                )
            }
            is HoeEvent.OnStoppedHolding -> {
                event.player.removePotionEffect(PotionEffectType.FAST_DIGGING)
            }
            else -> Unit
        }
    }
}