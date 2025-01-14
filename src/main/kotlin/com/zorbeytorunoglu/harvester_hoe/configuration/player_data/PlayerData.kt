package com.zorbeytorunoglu.harvester_hoe.configuration.player_data

data class PlayerData(
    val name: String? = null,
    val harvestedBlocks: Map<String, Int> = mapOf(),
    val enhancements: Map<String, PlayerEnhancementConfig> = mapOf(),
    val token: Int = 0,
    val harvestsInBackpack: Int = 0,
    val xp: Int = 0
)