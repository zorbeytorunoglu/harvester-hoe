package com.zorbeytorunoglu.harvester_hoe.configuration.main_config

import com.zorbeytorunoglu.harvester_hoe.Core
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

data class MainConfig(
    val applyToAllHoes: Boolean = true,
    val harvestBlocks: List<Material>,
    val customHoeConfig: CustomHoeConfig
)

data class CustomHoeConfig(
    val name: String,
    val lore: List<String>,
    val material: Material,
    val namespacedKey: String
) {
    fun asItemStack(): ItemStack = ItemStack(material).apply {
        itemMeta = itemMeta?.apply {
            setDisplayName(name)
            lore = this@CustomHoeConfig.lore
            persistentDataContainer.set(
                Core.namespacedKey, PersistentDataType.STRING, namespacedKey
            )
        }
    }
}