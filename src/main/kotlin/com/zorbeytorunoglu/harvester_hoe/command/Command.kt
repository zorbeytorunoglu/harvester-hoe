package com.zorbeytorunoglu.harvester_hoe.command

import org.bukkit.command.CommandSender

internal interface Command {
    val name: String
    val permission: String?
    val subCommands: MutableMap<String, Command>

    fun handleExecute(sender: CommandSender, args: Array<String>): Boolean
    fun handleTabCompletion(sender: CommandSender, args: Array<String>): List<String>
}