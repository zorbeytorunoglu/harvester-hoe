package com.zorbeytorunoglu.harvester_hoe.command.commands.token

import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import org.bukkit.command.CommandSender

internal class TokenCommand: BaseCommand() {

    override val name: String = "token"
    override val permission: String = "harvesterhoe.token"

    init {
        subCommands["add"] = TokenAddCommand()
        subCommands["remove"] = TokenRemoveCommand()
        subCommands["set"] = TokenSetCommand()
        subCommands["check"] = TokenCheckCommand()
    }

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            // todo: usage
            return true
        }
        val subCommand = subCommands[args[0]] ?: return false
        return subCommand.handleExecute(sender, args.drop(1).toTypedArray())
    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> {
        return when {
            args.isEmpty() -> subCommands.keys.toList()
            args.size == 1 -> subCommands.keys
                .filter { it.startsWith(args[0], ignoreCase = true) }
            else -> subCommands[args[0]]?.handleTabCompletion(sender, args.drop(1).toTypedArray())
                ?: emptyList()
        }
    }

}