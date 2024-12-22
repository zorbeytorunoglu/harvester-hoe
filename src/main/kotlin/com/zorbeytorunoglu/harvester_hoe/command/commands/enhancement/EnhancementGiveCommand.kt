package com.zorbeytorunoglu.harvester_hoe.command.commands.enhancement

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

internal class EnhancementGiveCommand: BaseCommand() {

    override val name: String
        get() = "give"

    override val permission: String?
        get() = "harvesterhoe.enhancement.give"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.size < 3) {
            sender.sendMessage("&cUsage: /harvesterhoe enhancement give <player> <enhancement>")
            return true
        }

        val enhancementId = args[0]
        val level = args.getOrNull(1)?.toIntOrNull() ?: 1

        Core.playerDataManager.toggleEnhancement((sender as Player), enhancementId)

        sender.sendMessage("Tamam verdim")

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> {
        return when (args.size) {
            1 -> sender.server.onlinePlayers
                .map { it.name }
                .filter { it.startsWith(args[0], ignoreCase = true) }
            2 -> Core.enhancementManager.getEnabledEnhancements()
                .filter { it.id.startsWith(args[1], ignoreCase = true) }
                .map { it.id }
            3 -> listOf(
                "1", "2", "3", "4", "5"
            )
            else -> emptyList()
        }
    }

}