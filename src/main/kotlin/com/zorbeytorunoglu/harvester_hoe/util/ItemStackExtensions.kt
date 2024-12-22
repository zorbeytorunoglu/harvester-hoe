package com.zorbeytorunoglu.harvester_hoe.util

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.config.asItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

fun Player.isHoldingHoe(): Boolean =
    inventory.itemInMainHand.itemMeta?.persistentDataContainer?.has(Core.namespacedKey, PersistentDataType.STRING) == true

fun Player.isHoldingAnyHoe(): Boolean =
    inventory.itemInMainHand.type.toString().endsWith("HOE")

fun ItemStack.isCustomHoe(): Boolean =
    itemMeta?.persistentDataContainer?.has(Core.namespacedKey, PersistentDataType.STRING) == true

fun ItemStack.isHoe(): Boolean =
    type.toString().endsWith("HOE")

fun Player.giveHoe() {
    inventory.addItem(
        Core.config.customHoeConfig.asItemStack()
    )
}