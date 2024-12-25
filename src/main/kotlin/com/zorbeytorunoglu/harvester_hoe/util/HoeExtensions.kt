package com.zorbeytorunoglu.harvester_hoe.util

import com.zorbeytorunoglu.harvester_hoe.Core
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

fun Player.isHoldingHoe(): Boolean = if (Core.mainConfigManager.get().applyToAllHoes) {
        isHoldingAnyHoe()
    } else {
        isHoldingCustomHoe()
    }

fun Player.isHoldingCustomHoe(): Boolean =
    inventory.itemInMainHand.isCustomHoe()

fun Player.isHoldingAnyHoe(): Boolean =
    inventory.itemInMainHand.isHoe()

fun ItemStack.isCustomHoe(): Boolean =
    itemMeta?.persistentDataContainer?.has(Core.namespacedKey, PersistentDataType.STRING) == true

fun ItemStack.isHoe(): Boolean =
    type.name.endsWith("HOE")

fun Player.giveHoe() {
    inventory.addItem(
        Core.mainConfigManager.get().customHoeConfig.asItemStack()
    )
}