package com.zorbeytorunoglu.harvester_hoe.command.commands.backpack

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import com.zorbeytorunoglu.harvester_hoe.command.playerNameTabCompletion
import com.zorbeytorunoglu.harvester_hoe.util.getPlayerOrNull
import com.zorbeytorunoglu.harvester_hoe.util.replaceAmount
import com.zorbeytorunoglu.harvester_hoe.util.replacePlayerName
import org.bukkit.command.CommandSender

internal class BackpackSetCommand: BaseCommand() {

    override val name: String = "set"
    override val permission: String = "harvesterhoe.backpack.set"

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

        Core.services.playerDataService.updateHarvestsInBackpack(
            uuid = player.uniqueId.toString(),
            amount = amount,
        )

        sender.sendMessage(
            messages.backpackSet
                .replacePlayerName(player.name)
                .replaceAmount(amount)
        )

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> {
        return when (args.size) {
            1 -> playerNameTabCompletion(args[0])
            else -> emptyList()
        }
    }
}