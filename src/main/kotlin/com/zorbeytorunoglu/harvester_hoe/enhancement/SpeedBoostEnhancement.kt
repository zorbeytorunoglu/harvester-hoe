package com.zorbeytorunoglu.harvester_hoe.enhancement

import com.zorbeytorunoglu.harvester_hoe.Core
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class SpeedBoostEnhancement: Enhancement {
    override val id: String
        get() = "speed_boost"
    override val name: String
        get() = "Speed Boost"
    override val description: String
        get() = "Gives you speed"

    override fun canHandle(event: HoeEvent): Boolean =
        event is HoeEvent.OnHold || event is HoeEvent.OnStoppedHolding

    override fun handle(event: HoeEvent) {
        when (event) {
            is HoeEvent.OnHold -> {

                // todo: dto yap, sonra onlardan model yap, enhancements config'de kaldın en son

                event.player.addPotionEffect(
                    PotionEffect(PotionEffectType.SPEED, Int.MAX_VALUE, 1)
                )
            }
            is HoeEvent.OnStoppedHolding -> {
                event.player.removePotionEffect(PotionEffectType.SPEED)
            }
            else -> Unit
        }
    }
}