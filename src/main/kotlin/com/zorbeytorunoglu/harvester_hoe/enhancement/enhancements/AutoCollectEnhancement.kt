package com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.AutoCollectConfig
import com.zorbeytorunoglu.harvester_hoe.enhancement.Enhancement
import com.zorbeytorunoglu.harvester_hoe.event.HoeEvent
import com.zorbeytorunoglu.harvester_hoe.event.ItemStoreUseCase
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack

private const val ENHANCEMENT_ID = "auto_collect"

class AutoCollectEnhancement: Enhancement {

    override val id: String = ENHANCEMENT_ID
    override val config: AutoCollectConfig = Core.enhancementsConfigManager.get().autoCollectConfig
    override val name: String = config.name
    override val description: String = config.description

    override fun canHandle(event: HoeEvent): Boolean = when (event) {
        is HoeEvent.OnHarvest -> { isEnabledForPlayer(event.player) }
        is HoeEvent.OnAutoCollect -> { isEnabledForPlayer(event.player) }
        else -> false
    }

    override fun handle(event: HoeEvent) {
        when (event) {
            is HoeEvent.OnAutoCollect -> {
                handleOnAutoCollect(event)
            }
            is HoeEvent.OnHarvest -> {
                handleOnHarvest(event)
            }
            else -> Unit
        }
    }

    private fun handleOnHarvest(event: HoeEvent.OnHarvest) {
        handleAutoCollect(
            player = event.player,
            block = event.block,
            bukkitEvent = event.event,
            excludeBottommost = false
        )
    }

    private fun handleOnAutoCollect(event: HoeEvent.OnAutoCollect) {
        handleAutoCollect(
            player = event.player,
            block = event.block,
            bukkitEvent = event.event,
            excludeBottommost = event.excludeBottommost
        )
    }

    private fun handleAutoCollect(
        player: Player,
        block: Block,
        bukkitEvent: BlockBreakEvent,
        excludeBottommost: Boolean
    ) {
        val collectedBlocks = collectBlocks(
            startBlock = block, includeUpward = true, excludeBottommost = excludeBottommost
        )

        val totalDrops = collectedBlocks.flatMap { it.drops }.groupBy { it.type }.map { (type, items) ->
            ItemStack(type, items.sumOf { it.amount })
        }

        ItemStoreUseCase(player = player, items = totalDrops).invoke()

        player.giveExp(bukkitEvent.expToDrop * (collectedBlocks.size + 1))

        collectedBlocks.forEach { block -> block.type = Material.AIR }
    }

    private fun collectBlocks(
        startBlock: Block,
        includeUpward: Boolean = false,
        includeDownward: Boolean = false,
        excludeBottommost: Boolean = false
    ): List<Block> {
        val blocks = mutableListOf<Block>()
        val visitedLocations = mutableSetOf(startBlock.location)

        val bottomBlock = if (excludeBottommost) {
            var current = startBlock
            while (current.getRelative(0, -1, 0).type == startBlock.type) {
                current = current.getRelative(0, -1, 0)
            }
            current
        } else null

        if (includeUpward) {
            var currentBlock = startBlock
            while (currentBlock.type == startBlock.type && currentBlock.location.y < 255) {
                if (!excludeBottommost || currentBlock != bottomBlock) {
                    blocks.add(currentBlock)
                    visitedLocations.add(currentBlock.location)
                }
                currentBlock = currentBlock.getRelative(0, 1, 0)
            }
        }

        if (includeDownward) {
            var currentBlock = startBlock
            while (currentBlock.type == startBlock.type && currentBlock.location.y > 0) {
                if (currentBlock.location !in visitedLocations &&
                    (!excludeBottommost || currentBlock != bottomBlock)) {
                    blocks.add(currentBlock)
                }
                currentBlock = currentBlock.getRelative(0, -1, 0)
            }
        }

        return blocks
    }

}