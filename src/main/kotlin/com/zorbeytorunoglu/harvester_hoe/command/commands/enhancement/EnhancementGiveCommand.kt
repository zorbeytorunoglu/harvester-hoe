package com.zorbeytorunoglu.harvester_hoe.command.commands.enhancement

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import com.zorbeytorunoglu.harvester_hoe.util.getPlayerOrNull
import com.zorbeytorunoglu.harvester_hoe.util.replaceEnhancement
import com.zorbeytorunoglu.harvester_hoe.util.replacePlayerName
import org.bukkit.command.CommandSender

internal class EnhancementGiveCommand: BaseCommand() {

    override val name: String
        get() = "give"

    override val permission: String?
        get() = "harvesterhoe.enhancement.give"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.size < 2) return true

        val player = args[0].getPlayerOrNull() ?: run {
            sender.sendMessage(messages.playerNotFound)
            return true
        }

        val enhancementId = args.getOrNull(1)
            .takeIf { Core.enhancementManager.getEnhancements().map { enh -> enh.id }.contains(it) } ?: run {
                return false
        }

        if (Core.services.enhancementService.getEnhancements(player.uniqueId.toString()).contains(enhancementId)) {
            sender.sendMessage(messages.playerAlreadyHaveEnhancement)
            return true
        }

        val level = args.getOrNull(2)?.toIntOrNull() ?: 1

        Core.services.enhancementService.giveEnhancement(player.uniqueId.toString(), enhancementId, level)

        sender.sendMessage(
            messages.enhancementGiven
                .replaceEnhancement(enhancementId)
                .replacePlayerName(player.name)
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
            3 -> listOf(
                "1", "2", "3", "4", "5"
            )
            else -> emptyList()
        }
    }

}