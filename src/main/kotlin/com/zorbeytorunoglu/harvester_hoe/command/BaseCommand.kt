package com.zorbeytorunoglu.harvester_hoe.command

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.messages_config.Messages
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

internal abstract class BaseCommand: Command {

    protected val messages: Messages = Core.messagesConfigManager.get()

    override val subCommands: MutableMap<String, Command> = mutableMapOf()

    final override fun handleExecute(sender: CommandSender, args: Array<String>): Boolean {
        if (!sender.hasCommandPermission()) {
            sender.sendMessage(messages.noPermission)
            return true
        }
        return execute(sender, args)
    }

    final override fun handleTabCompletion(sender: CommandSender, args: Array<String>): List<String> {
        if (!sender.hasCommandPermission()) return emptyList()
        return tabComplete(sender, args)
    }

    protected abstract fun execute(sender: CommandSender, args: Array<String>): Boolean

    protected abstract fun tabComplete(sender: CommandSender, args: Array<String>): List<String>

    protected fun CommandSender.hasCommandPermission(): Boolean {
        if (permission == null) return true
        return hasPermission(permission!!)
    }

}

internal fun Command.playerNameTabCompletion(arg: String): List<String> =
    Bukkit.getServer().onlinePlayers.map { it.name }.filter { it.startsWith(arg, ignoreCase = true) }