package com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.AutoCollectConfig
import com.zorbeytorunoglu.harvester_hoe.enhancement.Enhancement
import com.zorbeytorunoglu.harvester_hoe.enhancement.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.enhancement.dispatchEvent
import com.zorbeytorunoglu.harvester_hoe.enhancement.with
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack

private const val ENHANCEMENT_ID = "auto_collect"

class AutoCollectEnhancement: Enhancement {

    override val id: String = ENHANCEMENT_ID
    override val config: AutoCollectConfig = Core.enhancementsConfigManager.get().autoCollectConfig
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

        val collectedBlocks = collectBlocks(event.block, includeUpward = true)

        val totalDrops = collectedBlocks.flatMap { it.drops }.groupBy { it.type }.map { (type, items) ->
            ItemStack(type, items.sumOf { it.amount })
        }

        var leftovers = 0
        totalDrops.forEach { drop ->
            leftovers = event.player.inventory.addItem(drop).size
            dispatchEvent(
                HoeEvent.OnBackpackStore(
                    player = event.player,
                    amount = leftovers
                )
            )
        }

        event.player.giveExp(event.event.expToDrop * (collectedBlocks.size + 1))

        dispatchEvent(
            HoeEvent.OnHarvestCollected(
                event.player,
                event.block.type,
                totalDrops.size - leftovers
            )
        )

        collectedBlocks.forEach { block -> block.type = Material.AIR }
    }

    private fun collectBlocks(
        startBlock: Block,
        includeUpward: Boolean = false,
        includeDownward: Boolean = false
    ): List<Block> {
        val blocks = mutableListOf<Block>()
        val visitedLocations = mutableSetOf(startBlock.location)

        if (includeUpward) {
            var currentBlock = startBlock
            while (currentBlock.type == startBlock.type && currentBlock.location.y < 255) {
                blocks.add(currentBlock)
                visitedLocations.add(currentBlock.location)
                currentBlock = currentBlock.getRelative(0, 1, 0)
            }
        }

        if (includeDownward) {
            var currentBlock = startBlock
            while (currentBlock.type == startBlock.type && currentBlock.location.y > 0) {
                if (currentBlock.location !in visitedLocations) {
                    blocks.add(currentBlock)
                }
                currentBlock = currentBlock.getRelative(0, -1, 0)
            }
        }

        return blocks
    }

}