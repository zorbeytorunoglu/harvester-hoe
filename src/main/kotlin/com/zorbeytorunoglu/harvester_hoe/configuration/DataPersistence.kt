package com.zorbeytorunoglu.harvester_hoe.configuration

interface DataPersistence <T> {
    fun load(resource: Resource): Map<String, T>
    fun save(resource: Resource, data: Map<String, T>)
}