package com.zorbeytorunoglu.harvester_hoe.command.commands.enhancement

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import com.zorbeytorunoglu.harvester_hoe.util.colorHex
import com.zorbeytorunoglu.harvester_hoe.util.getPlayerOrNull
import com.zorbeytorunoglu.harvester_hoe.util.replacePlayerName
import org.bukkit.command.CommandSender

internal class EnhancementListCommand: BaseCommand() {

    override val name: String
        get() = "list"
    override val permission: String?
        get() = "harvesterhoe.enhancement.list"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {

        if (args.isEmpty()) {
            sender.sendMessage(messages.enhancementList)
            Core.enhancementManager.getEnabledEnhancements().map { it.id }.forEach { sender.sendMessage(it) }
            return true
        } else if (args.size == 1) {
            val player = args[0].getPlayerOrNull() ?: run {
                sender.sendMessage(messages.playerNotFound)
                return true
            }
            sender.sendMessage(messages.playerEnhancementList.replacePlayerName(player.name))

            Core.services.enhancementService.getEnhancements(player.uniqueId.toString()).map {
                "&7- &6ID: ${it.key}&7, &eLevel: ${it.value.tier}".colorHex
            }.forEach { sender.sendMessage(it) }

            return true
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> {
        return when (args.size) {
            1 -> sender.server.onlinePlayers
                .map { it.name }
                .filter { it.startsWith(args[0], ignoreCase = true) }
            else -> emptyList()
        }
    }

}