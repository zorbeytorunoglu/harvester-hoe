package com.zorbeytorunoglu.harvester_hoe.command.commands.enhancement

import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import org.bukkit.command.CommandSender

internal class EnhancementCommand: BaseCommand() {

    override val name: String = "enhancement"

    override val permission: String = "harvesterhoe.enhancement"

    init {
        subCommands["give"] = EnhancementGiveCommand()
        subCommands["list"] = EnhancementListCommand()
    }

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("evet enhancement, subcommandlar: ${subCommands.keys}")
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