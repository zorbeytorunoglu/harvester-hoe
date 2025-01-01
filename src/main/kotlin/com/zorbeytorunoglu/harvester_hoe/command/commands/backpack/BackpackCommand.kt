package com.zorbeytorunoglu.harvester_hoe.command.commands.backpack

import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import org.bukkit.command.CommandSender

internal class BackpackCommand: BaseCommand() {

    override val name: String = "backpack"

    override val permission: String = "harvesterhoe.backpack"

    init {
        subCommands["add"] = BackpackAddCommand()
        subCommands["set"] = BackpackSetCommand()
        subCommands["remove"] = BackpackRemoveCommand()
        subCommands["check"] = BackpackCheckCommand()
    }

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isEmpty()) return true
        subCommands[args[0]]?.handleExecute(sender, args.drop(1).toTypedArray()) ?: run { return false }
        return true
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