package com.zorbeytorunoglu.harvester_hoe.command.commands.xp

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import com.zorbeytorunoglu.harvester_hoe.command.playerNameTabCompletion
import com.zorbeytorunoglu.harvester_hoe.util.getPlayerOrNull
import com.zorbeytorunoglu.harvester_hoe.util.replaceAmount
import com.zorbeytorunoglu.harvester_hoe.util.replacePlayerName
import org.bukkit.command.CommandSender

internal class XpRemoveCommand: BaseCommand() {

    override val name: String = "remove"
    override val permission: String = "harvesterhoe.xp.remove"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isEmpty()) return false

        val player = args.getOrNull(0)?.getPlayerOrNull() ?: run {
            sender.sendMessage(messages.playerNotFound)
            return true
        }

        val amount = args.getOrNull(1)?.toIntOrNull()?.takeIf { it > 0 } ?: run {
            sender.sendMessage(messages.invalidAmount)
            return true
        }

        Core.services.playerDataService.removeXp(player.uniqueId.toString(), amount)

        sender.sendMessage(
            messages.xpRemove
                .replacePlayerName(player.name)
                .replaceAmount(amount)
                .replace("%xp-balance%", Core.services.playerDataService.getXp(player.uniqueId.toString()).toString())
        )

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> = when (args.size) {
        1 -> playerNameTabCompletion(args[0])
        2 -> listOf("<number>")
        else -> emptyList()
    }

}