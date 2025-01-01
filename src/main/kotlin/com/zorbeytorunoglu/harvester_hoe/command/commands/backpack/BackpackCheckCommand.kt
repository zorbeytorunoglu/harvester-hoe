package com.zorbeytorunoglu.harvester_hoe.command.commands.backpack

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import com.zorbeytorunoglu.harvester_hoe.command.playerNameTabCompletion
import com.zorbeytorunoglu.harvester_hoe.util.getPlayerOrNull
import com.zorbeytorunoglu.harvester_hoe.util.replaceAmount
import com.zorbeytorunoglu.harvester_hoe.util.replacePlayerName
import org.bukkit.command.CommandSender

internal class BackpackCheckCommand: BaseCommand() {

    override val name: String = "check"
    override val permission: String = "harvesterhoe.backpack.check"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isEmpty()) return true

        val player = args.getOrNull(0)?.getPlayerOrNull() ?: run {
            sender.sendMessage(messages.playerNotFound)
            return true
        }

        val harvestSize = Core.services.playerDataService.getHarvestsInBackpack(player.uniqueId.toString())

        sender.sendMessage(
            messages.backpackCheck
                .replacePlayerName(player.name)
                .replaceAmount(harvestSize)
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