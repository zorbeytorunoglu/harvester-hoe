package com.zorbeytorunoglu.harvester_hoe.command.commands.xp

import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import org.bukkit.command.CommandSender

internal class XpCommand: BaseCommand() {

    override val name: String = "xp"
    override val permission: String = "harvesterhoe.xp"

    init {
        subCommands["add"] = XpAddCommand()
        subCommands["remove"] = XpRemoveCommand()
        subCommands["check"] = XpCheckCommand()
        subCommands["set"] = XpSetCommand()
    }

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isEmpty()) return true
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