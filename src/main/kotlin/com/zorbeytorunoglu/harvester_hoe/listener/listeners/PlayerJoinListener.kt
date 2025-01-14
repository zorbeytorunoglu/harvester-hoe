package com.zorbeytorunoglu.harvester_hoe.listener.listeners

import com.zorbeytorunoglu.harvester_hoe.Core
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener: Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        // getting the name of the user from uuid is quite an expensive operation. That's why we are doing this.
        Core.services.playerDataService.setPlayerName(
            uuid = event.player.uniqueId.toString(),
            name = event.player.name
        )
    }

}