package com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.ExcavatorConfig
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.ExcavatorTier
import com.zorbeytorunoglu.harvester_hoe.enhancement.TieredEnhancement
import com.zorbeytorunoglu.harvester_hoe.enhancement.dispatchEvent
import com.zorbeytorunoglu.harvester_hoe.enhancement.with
import com.zorbeytorunoglu.harvester_hoe.event.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.util.isHarvestMaterial
import com.zorbeytorunoglu.harvester_hoe.util.isLucky
import org.bukkit.block.Block

const val EXCAVATOR_ENHANCEMENT_ID = "excavator"

class ExcavatorEnhancement: TieredEnhancement<ExcavatorTier> {

    override val id: String = EXCAVATOR_ENHANCEMENT_ID
    override val config: ExcavatorConfig = Core.enhancementsConfigManager.get().excavatorConfig
    override val name: String = config.name
    override val description: String = config.name
    override val tiers: Map<Int, ExcavatorTier> = config.tiers

    override fun canHandle(event: HoeEvent): Boolean = when (event) {
        is HoeEvent.OnHarvest -> isEnabledForPlayer(event.player)
        else -> false
    }

    override fun handle(event: HoeEvent) = (event as HoeEvent.OnHarvest).with {

        val tier = getTier(event.player) ?: return@with

        if (!event.player.isLucky(tier.chance)) return@with

        val autoCollectEnabled = Core.services.enhancementService.isEnhancementEnabled(
            player = event.player,
            enhancementId = AUTO_COLLECT_ENHANCEMENT_ID
        )

        getBlocksInRadius(block, tier.radius).forEach {
            if (autoCollectEnabled) {
                dispatchEvent(
                    HoeEvent.OnAutoCollect(
                        player = event.player,
                        block = it,
                        excludeBottommost = true,
                        event = event.event
                    )
                )
            } else {
                it.breakNaturally()
            }
        }

    }

    private fun getBlocksInRadius(center: Block, radius: Int): List<Block> {
        val blocks = mutableListOf<Block>()
        val world = center.world
        val centerX = center.x
        val centerZ = center.z
        val y = center.y

        for (x in -radius..radius) {
            for (z in -radius..radius) {
                if (x * x + z * z > radius * radius) continue
                if (x == 0 && z == 0) continue

                world.getBlockAt(centerX + x, y, centerZ + z).takeIf { it.isHarvestMaterial() }?.let { blocks.add(it) }
            }
        }

        return blocks
    }

}