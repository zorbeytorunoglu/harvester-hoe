package com.zorbeytorunoglu.harvester_hoe.config

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.util.colorHex
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

data class Config(
    val applyToAllHoes: Boolean = true,
    val harvestBlocks: List<Material>,
    val customHoeConfig: CustomHoeConfig
)

data class CustomHoeConfig(
    val name: String,
    val lore: List<String>,
    val material: String,
    val namespacedKey: String
)

fun CustomHoeConfig.asItemStack(): ItemStack {
    val item = ItemStack(Material.valueOf(material))
    val meta = item.itemMeta!!
    meta.lore = lore.map { it.colorHex }
    meta.setDisplayName(name.colorHex)
    meta.persistentDataContainer.set(Core.namespacedKey, PersistentDataType.STRING, "harvester_hoe")
    item.itemMeta = meta
    return item
}

fun Block.isHarvestBlock(): Boolean = Core.config.harvestBlocks.contains(type)

fun Resource.loadConfig(): Config {
    load()
    return Config(
        applyToAllHoes = getBoolean("apply-to-all-hoes"),
        harvestBlocks = getStringList("harvest-blocks").map { Material.valueOf(it) },
        customHoeConfig = CustomHoeConfig(
            name = getString("custom-hoe.name")!!,
            lore = getStringList("custom-hoe.lore"),
            material = getString("custom-hoe.material")!!,
            namespacedKey = getString("custom-hoe.namespaced-key")!!
        )
    )
}