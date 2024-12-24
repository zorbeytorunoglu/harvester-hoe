package com.zorbeytorunoglu.harvester_hoe.command.commands.token

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import com.zorbeytorunoglu.harvester_hoe.util.getPlayerOrNull
import com.zorbeytorunoglu.harvester_hoe.util.replacePlayerName
import com.zorbeytorunoglu.harvester_hoe.util.replaceToken
import org.bukkit.command.CommandSender

internal class TokenCheckCommand: BaseCommand() {

    override val name: String = "check"
    override val permission: String? = "harvesterhoe.token.check"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isEmpty()) return true

        val player = args[0].getPlayerOrNull() ?: run {
            sender.sendMessage(messages.playerNotFound)
            return true
        }

        Core.services.tokenService.getTokens(player).let { token ->
            sender.sendMessage(
                messages.tokenCheck
                    .replaceToken(token)
                    .replacePlayerName(player.name)
            )
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> {
        return when (args.size) {
            1 -> sender.server.onlinePlayers.map { it.name }.filter { it.startsWith(args[0], ignoreCase = true) }
            else -> emptyList()
        }
    }

}