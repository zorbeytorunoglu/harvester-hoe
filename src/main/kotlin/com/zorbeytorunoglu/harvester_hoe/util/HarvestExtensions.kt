package com.zorbeytorunoglu.harvester_hoe.util

import com.zorbeytorunoglu.harvester_hoe.Core
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack

fun Block.isHarvestMaterial(): Boolean =
    Core.mainConfigManager.get().harvestBlocks.contains(type)

fun ItemStack.isHarvestMaterial(): Boolean =
    Core.mainConfigManager.get().harvestBlocks.contains(type)

fun Material.isHarvestMaterial(): Boolean =
    Core.mainConfigManager.get().harvestBlocks.contains(this)