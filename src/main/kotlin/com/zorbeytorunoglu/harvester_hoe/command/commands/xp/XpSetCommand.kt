package com.zorbeytorunoglu.harvester_hoe.command.commands.xp

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import com.zorbeytorunoglu.harvester_hoe.command.playerNameTabCompletion
import com.zorbeytorunoglu.harvester_hoe.util.getPlayerOrNull
import com.zorbeytorunoglu.harvester_hoe.util.replaceAmount
import com.zorbeytorunoglu.harvester_hoe.util.replacePlayerName
import com.zorbeytorunoglu.harvester_hoe.util.replaceXp
import org.bukkit.command.CommandSender

internal class XpSetCommand: BaseCommand() {

    override val name: String = "set"
    override val permission: String = "harvesterhoe.xp.set"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isEmpty()) return false

        val player = args[0].getPlayerOrNull() ?: run {
            sender.sendMessage(messages.playerNotFound)
            return true
        }

        val amount = args[1].toIntOrNull() ?: run {
            sender.sendMessage(messages.invalidAmount)
            return true
        }

        Core.services.playerDataService.setXp(player.uniqueId.toString(), amount)

        sender.sendMessage(
            messages.xpSet
                .replacePlayerName(player.name)
                .replaceAmount(amount)
        )

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> {
        return when (args.size) {
            1 -> playerNameTabCompletion(args[0])
            2 -> listOf("<number>")
            else -> emptyList()
        }
    }

}