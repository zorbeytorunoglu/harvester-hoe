package com.zorbeytorunoglu.harvester_hoe.util

import com.zorbeytorunoglu.harvester_hoe.Core
import org.bukkit.block.Block

fun Block.isHarvestBlock(): Boolean =
    Core.mainConfigManager.get().harvestBlocks.contains(type)