package com.zorbeytorunoglu.harvester_hoe.command.commands.enhancement

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.command.BaseCommand
import com.zorbeytorunoglu.harvester_hoe.util.colorHex
import org.bukkit.command.CommandSender

internal class EnhancementListCommand: BaseCommand() {

    override val name: String
        get() = "list"
    override val permission: String?
        get() = "harvesterhoe.enhancement.list"

    override fun execute(sender: CommandSender, args: Array<String>): Boolean {
        sender.sendMessage("&aEnabled enhancements:".colorHex)
        Core.enhancementManager.getEnabledEnhancements().map { it.id }.forEach { sender.sendMessage("&c- $it".colorHex) }
        return true
    }

    override fun tabComplete(sender: CommandSender, args: Array<String>): List<String> {
        return emptyList()
    }

}