package com.zorbeytorunoglu.harvester_hoe.enhancement

interface Enhancement {
    val id: String
    val name: String
    val description: String

    fun canHandle(event: HoeEvent): Boolean
    fun handle(event: HoeEvent)
}