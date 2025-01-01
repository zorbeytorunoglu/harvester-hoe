package com.zorbeytorunoglu.harvester_hoe.command.commands

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.HarvesterHoe
import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import com.zorbeytorunoglu.harvester_hoe.command.commands.backpack.BackpackCommand
import com.zorbeytorunoglu.harvester_hoe.command.commands.enhancement.EnhancementCommand
import com.zorbeytorunoglu.harvester_hoe.command.commands.token.TokenCommand
import com.zorbeytorunoglu.harvester_hoe.command.commands.xp.XpCommand
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

internal class HarvesterHoeCommand: BaseCommand() {

    override val name: String = "harvesterhoe"

    override val permission: String = "harvesterhoe.command"

    init {
        subCommands["enhancement"] = EnhancementCommand()
        subCommands["token"] = TokenCommand()
        subCommands["xp"] = XpCommand()
        subCommands["backpack"] = BackpackCommand()
    }

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isEmpty()) return true

        val subCommand = subCommands[args[0]] ?: run { return true }

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

internal fun Core.registerMainCommand(plugin: HarvesterHoe) {
    val mainCommand = HarvesterHoeCommand()

    plugin.getCommand("harvesterhoe")?.apply {
        setExecutor(CommandExecutor { sender, command, label, args ->
            mainCommand.handleExecute(sender, args)
            true
        })
        tabCompleter = TabCompleter { sender, command, label, args ->
            mainCommand.handleTabCompletion(sender, args)
        }
    }
}