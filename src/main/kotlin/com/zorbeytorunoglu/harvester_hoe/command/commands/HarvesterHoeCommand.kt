package com.zorbeytorunoglu.harvester_hoe.command.commands

import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import com.zorbeytorunoglu.harvester_hoe.command.commands.enhancement.EnhancementCommand
import org.bukkit.command.CommandSender

internal class HarvesterHoeCommand: BaseCommand() {

    override val name: String = "harvesterhoe"

    override val permission: String = "harvesterhoe.command"

    init {
        subCommands["enhancement"] = EnhancementCommand()
    }

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("Available commands: ${subCommands.keys.joinToString()}")
            return true
        }

        val subCommand = subCommands[args[0]] ?: run {
            sender.sendMessage("Available commands: ${subCommands.keys.joinToString()}")
            return true
        }

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