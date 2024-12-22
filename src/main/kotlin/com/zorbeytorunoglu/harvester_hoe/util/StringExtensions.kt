package com.zorbeytorunoglu.harvester_hoe.util

import org.bukkit.ChatColor
import java.util.regex.Pattern

val String.colorHex get(): String {
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