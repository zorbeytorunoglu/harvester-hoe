package com.zorbeytorunoglu.harvester_hoe.command

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.messages_config.Messages
import com.zorbeytorunoglu.harvester_hoe.util.colorHex
import org.bukkit.command.CommandSender

internal abstract class BaseCommand: Command {

    protected val messages: Messages = Core.messagesConfigManager.get()

    override val subCommands: MutableMap<String, Command> = mutableMapOf()

    final override fun handleExecute(sender: CommandSender, args: Array<String>): Boolean {
        if (!sender.hasCommandPermission()) {
            sender.sendMessage("&cYou don't have permission to use this command!".colorHex)
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