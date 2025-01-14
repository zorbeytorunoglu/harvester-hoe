package com.zorbeytorunoglu.harvester_hoe.use_case

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.event.HoeEvent
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

internal class ItemStoreUseCase(
    private val player: Player,
    private val items: List<ItemStack>
): UseCase {

    override operator fun invoke() {

        val storedItems = mutableListOf<ItemStack>()
        val leftoverItems = mutableListOf<ItemStack>()

        items.forEach { item ->
            val leftovers = player.inventory.addItem(item)
            if (leftovers.isEmpty()) {
                storedItems.add(item)
            } else {
                val stored = ItemStack(item.type, item.amount - leftovers.values.sumOf { it.amount })
                if (stored.amount > 0) storedItems.add(stored)
                leftovers.values.forEach { leftoverItems.add(it) }
            }
        }

        Core.enhancementManager.dispatchEvent(
            HoeEvent.OnBackpackStore(
                player = player,
                amount = leftoverItems.size
            )
        )

    }

}