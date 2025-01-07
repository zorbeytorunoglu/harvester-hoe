package com.zorbeytorunoglu.harvester_hoe.util

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.regex.Pattern

internal val String.colorHex get(): String {
    var message = this
    val pattern = Pattern.compile("#[a-fA-F0-9]{6}")
    var matcher = pattern.matcher(message)
    while (matcher.find()) {
        val hexCode = message.substring(matcher.start(), matcher.end())
        val replaceSharp = hexCode.replace('#', 'x')
        val ch = replaceSharp.toCharArray()
        val builder = StringBuilder("")
        for (c in ch) {
            builder.append("&$c")
        }
        message = message.replace(hexCode, builder.toString())
        matcher = pattern.matcher(message)
    }
    return ChatColor.translateAlternateColorCodes('&', message)
}
fun String.color(colorCode: Char): String {
    return ChatColor.translateAlternateColorCodes(colorCode, this)
}

internal fun String.getPlayerOrNull(): Player? = org.bukkit.Bukkit.getPlayer(this)

internal fun String.replaceAmount(amount: Int): String = this.replace("%amount%", amount.toString())

internal fun String.replacePlayerName(playerName: String): String = this.replace("%player%", playerName)

internal fun String.replaceToken(token: Int): String = this.replace("%token%", token.toString())

internal fun String.replaceEnhancement(enhancement: String): String = this.replace("%enhancement%", enhancement)

internal fun String.replaceXp(xp: Int): String = this.replace("%xp%", "$xp")