package com.zorbeytorunoglu.harvester_hoe.configuration

import org.bukkit.plugin.Plugin

interface ConfigLoader <T> {
    fun load(resource: Resource): T
}

sealed interface ConfigResult <T> {
    data class Success<T>(val value: T): ConfigResult<T>
    data class Error<T>(val throwable: Throwable): ConfigResult<T>
}

abstract class ConfigManager<T>(
    plugin: Plugin,
    fileName: String,
    private val loader: ConfigLoader<T>
) {
    private var resource: Resource = Resource(plugin, fileName)
    private var config: T? = null

    fun reload(): ConfigResult<T> {
        return try {
            resource.load()
            config = loader.load(resource)
            ConfigResult.Success(config!!)
        } catch (e: Exception) {
            ConfigResult.Error(e)
        }
    }

    fun get(): T = config ?: throw IllegalStateException("Config not loaded")
}