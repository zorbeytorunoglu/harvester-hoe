package com.zorbeytorunoglu.harvester_hoe.configuration.player_data

data class PlayerData(
    val harvestedBlocks: Map<String, Int> = mapOf(),
    val enhancements: Map<String, PlayerEnhancementConfig> = mapOf(),
    val token: Int = 0,
    val harvestsInBackpack: Double = 0.0
)