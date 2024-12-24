package com.zorbeytorunoglu.harvester_hoe.command.commands.token

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import com.zorbeytorunoglu.harvester_hoe.util.getPlayerOrNull
import com.zorbeytorunoglu.harvester_hoe.util.replaceAmount
import com.zorbeytorunoglu.harvester_hoe.util.replacePlayerName
import org.bukkit.command.CommandSender

internal class TokenRemoveCommand: BaseCommand() {

    override val name: String = "remove"
    override val permission: String = "harvesterhoe.token.remove"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(messages.invalidUsage)
            return true
        }

        val player = args.getOrNull(0)?.getPlayerOrNull() ?: run {
            sender.sendMessage(messages.playerNotFound)
            return true
        }

        val amount = args.getOrNull(1)?.toIntOrNull()?.takeIf { it > 0 } ?: run {
            sender.sendMessage(messages.invalidAmount)
            return true
        }

        Core.playerDataManager.tokenService.takeTokens(player, amount)

        sender.sendMessage(
            messages.tokenRemoved
                .replaceAmount(amount)
                .replacePlayerName(player.name)
        )

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> {
        return when (args.size) {
            1 -> sender.server.onlinePlayers.map { it.name }.filter { it.startsWith(args[0], ignoreCase = true) }
            else -> emptyList()
        }
    }

}