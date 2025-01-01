package com.zorbeytorunoglu.harvester_hoe.command.commands.xp

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import com.zorbeytorunoglu.harvester_hoe.command.playerNameTabCompletion
import com.zorbeytorunoglu.harvester_hoe.util.getPlayerOrNull
import com.zorbeytorunoglu.harvester_hoe.util.replacePlayerName
import com.zorbeytorunoglu.harvester_hoe.util.replaceXp
import org.bukkit.command.CommandSender

internal class XpCheckCommand: BaseCommand() {

    override val name: String = "check"
    override val permission: String = "harvesterhoe.xp.check"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isEmpty()) return true

        val player = args[0].getPlayerOrNull() ?: run {
            sender.sendMessage(messages.playerNotFound)
            return true
        }

        Core.services.playerDataService.getXp(player.uniqueId.toString()).let { xp ->
            sender.sendMessage(
                messages.xpCheck
                    .replacePlayerName(player.name)
                    .replaceXp(xp)
            )
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> {
        return when (args.size) {
            1 -> playerNameTabCompletion(args[0])
            else -> emptyList()
        }
    }

}