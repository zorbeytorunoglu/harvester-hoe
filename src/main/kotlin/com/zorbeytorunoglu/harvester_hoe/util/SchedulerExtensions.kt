package com.zorbeytorunoglu.harvester_hoe.util

import com.zorbeytorunoglu.harvester_hoe.Core
import org.bukkit.Bukkit

fun runTaskLater(tick: Long, task: () -> Unit) {
    Bukkit.getScheduler().runTaskLater(
        Core.plugin, Runnable {
            task()
        }, tick
    )
}