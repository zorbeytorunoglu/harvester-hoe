package com.zorbeytorunoglu.harvester_hoe.hook

interface Hook {
    fun canHook(): Boolean
    fun hook()
}