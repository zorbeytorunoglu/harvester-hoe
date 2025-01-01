package com.zorbeytorunoglu.harvester_hoe.command.commands.backpack

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import com.zorbeytorunoglu.harvester_hoe.command.playerNameTabCompletion
import com.zorbeytorunoglu.harvester_hoe.util.getPlayerOrNull
import com.zorbeytorunoglu.harvester_hoe.util.replaceAmount
import com.zorbeytorunoglu.harvester_hoe.util.replacePlayerName
import org.bukkit.command.CommandSender

internal class BackpackRemoveCommand: BaseCommand() {

    override val name: String = "remove"
    override val permission: String = "harvesterhoe.backpack.remove"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isEmpty()) return true

        val player = args.getOrNull(0)?.getPlayerOrNull() ?: run {
            sender.sendMessage(messages.playerNotFound)
            return true
        }

        val amount = args.getOrNull(1)?.toIntOrNull()?.takeIf { it > 0 } ?: run {
            sender.sendMessage(messages.invalidAmount)
            return true
        }

        Core.services.playerDataService.removeHarvestsToBackpack(
            player.uniqueId.toString(),
            amount
        )

        sender.sendMessage(
            messages.backpackRemove
                .replacePlayerName(player.name)
                .replaceAmount(amount)
        )

        return true

    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> = when (args.size) {
        1 -> playerNameTabCompletion(args[0])
        2 -> listOf("<number>")
        else -> emptyList()
    }

}