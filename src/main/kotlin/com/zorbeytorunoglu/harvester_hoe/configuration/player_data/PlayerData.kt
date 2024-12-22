package com.zorbeytorunoglu.harvester_hoe.configuration.player_data

data class PlayerData(
    val harvestedBlocks: Map<String, Int> = mapOf(),
    val upgrades: Map<String, PlayerEnhancementConfig> = mapOf()
)