package com.zorbeytorunoglu.harvester_hoe.command.commands.enhancement

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import com.zorbeytorunoglu.harvester_hoe.util.getPlayerOrNull
import com.zorbeytorunoglu.harvester_hoe.util.replaceEnhancement
import com.zorbeytorunoglu.harvester_hoe.util.replacePlayerName
import org.bukkit.command.CommandSender

internal class EnhancementTakeCommand: BaseCommand() {

    override val name: String = "take"
    override val permission: String = "harvesterhoe.enhancement.take"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.size < 2) return true

        val player = args[0].getPlayerOrNull() ?: run {
            sender.sendMessage(messages.playerNotFound)
            return true
        }

        val enhancementId = args.getOrNull(1) ?: run {
            sender.sendMessage(messages.invalidEnhancementId)
            return true
        }

        if (!Core.services.enhancementService.getEnhancements(player.uniqueId.toString()).contains(enhancementId)) {
            sender.sendMessage(messages.playerDontHaveEnhancement)
            return true
        }

        Core.services.enhancementService.removeEnhancement(player.uniqueId.toString(), enhancementId)

        sender.sendMessage(
            messages.enhancementTaken
                .replacePlayerName(player.name)
                .replaceEnhancement(enhancementId)
        )

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
            else -> emptyList()
        }
    }

}