package com.zorbeytorunoglu.harvester_hoe.hook.hooks

import com.zorbeytorunoglu.harvester_hoe.Core
import com.zorbeytorunoglu.harvester_hoe.configuration.enhancements_config.enhancements.BackpackTier
import com.zorbeytorunoglu.harvester_hoe.enhancement.enhancements.BACKPACK_ENHANCEMENT_ID
import com.zorbeytorunoglu.harvester_hoe.hook.Hook
import com.zorbeytorunoglu.harvester_hoe.hook.hooks.HHExpansion.PlaceholderHandler.DynamicPlaceholder
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer
import org.bukkit.plugin.Plugin

class PAPIHook(private val plugin: Plugin): Hook {

    override fun canHook(): Boolean =
        plugin.server.pluginManager.isPluginEnabled("PlaceholderAPI")

    override fun hook() {
        HHExpansion(plugin = plugin).register()
    }

}

private const val DEFAULT_FALLBACK_VALUE = "0"

private class HHExpansion(private val plugin: Plugin): PlaceholderExpansion() {

    sealed interface PlaceholderHandler {

        fun handle(player: OfflinePlayer? = null): String

        class Global(
            private val handler: () -> String,
            private val fallback: String = DEFAULT_FALLBACK_VALUE
        ): PlaceholderHandler {
            override fun handle(player: OfflinePlayer?): String =
                runCatching { handler() }.getOrDefault(fallback)
        }

        class PlayerSpecific(
            private val handler: (String) -> String,
            private val fallback: String = DEFAULT_FALLBACK_VALUE
        ): PlaceholderHandler {
            override fun handle(player: OfflinePlayer?): String =
                runCatching { player?.let { player.uniqueId.toString() }
                    ?.let { uuid -> handler(uuid) } ?: fallback }.getOrDefault(fallback)
        }

        sealed interface DynamicPlaceholder {

            fun matches(params: String): Boolean
            fun handle(params: String, player: OfflinePlayer? = null): String?

            class PlayerWithParam(
                private val prefix: String,
                private val handler: (String, String) -> String,
                private val fallback: String = DEFAULT_FALLBACK_VALUE
            ): DynamicPlaceholder {
                override fun matches(params: String): Boolean =
                    params.startsWith(prefix)

                override fun handle(params: String, player: OfflinePlayer?): String? {
                    if (!matches(params)) return null
                    val param = params.removePrefix(prefix)
                    return runCatching {
                        player?.let { p ->
                            handler(p.uniqueId.toString(), params)
                        } ?: fallback
                    }.getOrDefault(fallback)
                }
            }

            class GlobalWithNumber(
                private val prefix: String,
                private val handler: (Int) -> String,
                private val fallback: String = DEFAULT_FALLBACK_VALUE
            ) : DynamicPlaceholder {
                override fun matches(params: String): Boolean =
                    params.startsWith(prefix) && params.removePrefix(prefix).toIntOrNull() != null

                override fun handle(params: String, player: OfflinePlayer?): String? {
                    if (!matches(params)) return null
                    val position = params.removePrefix(prefix).toIntOrNull() ?: return fallback
                    return runCatching {
                        handler(position)
                    }.getOrDefault(fallback)
                }
            }

            class Pattern(
                private val regex: Regex,
                private val handler: (MatchResult) -> String,
                private val fallback: String = DEFAULT_FALLBACK_VALUE
            ) : DynamicPlaceholder {
                override fun matches(params: String): Boolean = regex.matches(params)

                override fun handle(params: String, player: OfflinePlayer?): String? {
                    val matchResult = regex.matchEntire(params) ?: return null
                    return runCatching {
                        handler(matchResult)
                    }.getOrDefault(fallback)
                }
            }

        }

    }

    private val dynamicPlaceholders = listOf(

        DynamicPlaceholder.PlayerWithParam(
            prefix = "player_has_enhancement_",
            handler = { uuid, enhancementId ->
                Core.services.enhancementService.hasEnhancement(uuid, enhancementId).toString()
            }
        ),

        DynamicPlaceholder.PlayerWithParam(
            prefix = "player_enhancement_tier_",
            handler = { uuid, enhancementId ->
                Core.services.enhancementService.getEnhancementLevel(uuid, enhancementId).toString()
            }
        ),

        DynamicPlaceholder.Pattern(
            regex = "top_harvest_([0-9]+)_name".toRegex(),
            handler = { matchResult ->
                val position = matchResult.groupValues[1].toInt()
                Core.services.playerDataService.getTopHarvesters(limit = position)
                    .getOrNull(position - 1)
                    ?.let { (uuid, _) ->
                        Core.services.playerDataService.getPlayerName(uuid)
                    } ?: "Nobody"
            }
        ),

        DynamicPlaceholder.Pattern(
            regex = "top_harvest_([0-9]+)_value".toRegex(),
            handler = { matchResult ->
                val position = matchResult.groupValues[1].toInt()
                Core.services.playerDataService.getTopHarvesters(limit = position)
                    .getOrNull(position - 1)
                    ?.let { (_, count) -> count.toString() }
                    ?: "0"
            }
        ),

    )

    private val placeholderHandlers: Map<String, PlaceholderHandler> = buildMap {

        putPlayer(
            identifier = "player_total_harvest_count",
            handler = { uuid ->
                Core.services.playerDataService.getHarvestedBlocks(uuid).values.sum().toString()
            }
        )

        putPlayer(
            identifier = "player_tokens",
            handler = { uuid ->
                Core.services.playerDataService.getTokens(uuid).toString()
            }
        )

        putPlayer(
            identifier = "player_xp",
            handler = { uuid ->
                Core.services.playerDataService.getXp(uuid).toString()
            }
        )

        putPlayer(
            identifier = "player_backpack_size",
            handler = { uuid ->
                if (!Core.services.enhancementService.isEnhancementEnabled(uuid, BACKPACK_ENHANCEMENT_ID)) DEFAULT_FALLBACK_VALUE
                Core.services.enhancementService.getPlayerTierConfig<BackpackTier>(
                    playerUuid = uuid,
                    enhancementId = BACKPACK_ENHANCEMENT_ID
                )?.size?.toString() ?: DEFAULT_FALLBACK_VALUE
            }
        )

        putPlayer(
            identifier = "player_harvests_in_backpack",
            handler = { uuid ->
                Core.services.playerDataService.getHarvestsInBackpack(uuid).toString()
            }
        )

    }

    override fun getAuthor(): String = "zorbeytorunoglu"

    override fun getIdentifier(): String = "harvester_hoe"

    override fun getVersion(): String = this.plugin.description.version

    override fun persist(): Boolean = true

    override fun onRequest(player: OfflinePlayer?, params: String): String? {
        placeholderHandlers[params]?.let { handler ->
            return handler.handle(player)
        }
        dynamicPlaceholders.forEach {
            it.handle(params, player)?.let { result ->
                return result
            }
        }
        return null
    }

    private fun MutableMap<String, PlaceholderHandler>.putGlobal(
        identifier: String,
        handler: () -> String,
        fallback: String = DEFAULT_FALLBACK_VALUE
    ) {
        put(identifier, PlaceholderHandler.Global(handler, fallback))
    }

    private fun MutableMap<String, PlaceholderHandler>.putPlayer(
        identifier: String,
        handler: (String) -> String,
        fallback: String = DEFAULT_FALLBACK_VALUE
    ) {
        put(identifier, PlaceholderHandler.PlayerSpecific(handler, fallback))
    }

}