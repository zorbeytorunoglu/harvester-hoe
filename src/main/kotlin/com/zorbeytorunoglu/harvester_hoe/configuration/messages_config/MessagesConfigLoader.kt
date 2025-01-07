package com.zorbeytorunoglu.harvester_hoe.configuration.messages_config

import com.zorbeytorunoglu.harvester_hoe.configuration.ConfigLoader
import com.zorbeytorunoglu.harvester_hoe.configuration.Resource
import com.zorbeytorunoglu.harvester_hoe.util.colorHex

class MessagesConfigLoader: ConfigLoader<Messages> {

    override fun load(resource: Resource): Messages {
        return Messages(
            playerNotFound = (resource.getString("user-not-found") ?: "&cUser not found!").colorHex,
            invalidAmount = (resource.getString("invalid-amount") ?: "&cInvalid amount!").colorHex,
            noPermission = (resource.getString("no-permission") ?: "&cYou don't have permission!").colorHex,
            invalidUsage = (resource.getString("invalid-usage") ?: "&cInvalid usage!").colorHex,
            tokenAdded = (resource.getString("token-added") ?: "&aToken added!").colorHex,
            tokenRemoved = (resource.getString("token-removed") ?: "&aToken removed!").colorHex,
            tokenSet = (resource.getString("token-set") ?: "&aToken set!").colorHex,
            tokenCheck = (resource.getString("token-check") ?: "&aToken balance of %player%: &6%token%").colorHex,
            invalidEnhancementId = (resource.getString("invalid-enhancement-id") ?: "&cInvalid enhancement id!").colorHex,
            playerDontHaveEnhancement = (resource.getString("player-dont-have-enhancement") ?: "&cPlayer doesn't have this enhancement!").colorHex,
            enhancementTaken = (resource.getString("enhancement-taken") ?: "&aEnhancement %enhancement% is taken from %player%").colorHex,
            enhancementGiven = (resource.getString("enhancement-given") ?: "&aEnhancement %enhancement% is given to %player%").colorHex,
            enhancementList = (resource.getString("enhancement-list") ?: "&aEnabled enhancements:").colorHex,
            playerEnhancementList = (resource.getString("player-enhancement-list") ?: "&aEnhancements of %player%").colorHex,
            playerAlreadyHaveEnhancement = (resource.getString("player-already-have-enhancement") ?: "&cPlayer already have this enhancement!").colorHex,
            noGreaterTier = (resource.getString("no-greater-tier") ?: "&cNo greater tier!").colorHex,
            noLesserTier = (resource.getString("no-lesser-tier") ?: "&cNo lesser tier!").colorHex,
            invalidTierConfig = (resource.getString("invalid-tier-config") ?: "&cInvalid tier config!").colorHex,
            playersEnhancementUpgraded = (resource.getString("players-enhancement-upgraded") ?: "&aYou upgraded %player%'s %enhancement% to %tier%!").colorHex,
            playersEnhancementDowngraded = (resource.getString("players-enhancement-downgraded") ?: "&aYou downgraded %player%'s %enhancement% to %tier%!").colorHex,
            xpCheck = (resource.getString("xp-check") ?: "&aXP of %player%: %xp%").colorHex,
            xpSet = (resource.getString("xp-set") ?: "&aYou set the XP of %player% to %amount%.").colorHex,
            xpAdd = (resource.getString("xp-add") ?: "&aYou added %amount% of XP to %player%. His new xp balance is: %xp-balance%.").colorHex,
            xpRemove = (resource.getString("xp-remove") ?: "&aYou added %amount% of XP to %player%. His new xp balance is: %xp-balance%.").colorHex,
            backpackCheck = (resource.getString("backpack-check") ?: "&aBackpack items  of player: %amount%").colorHex,
            backpackAdd = (resource.getString("backpack-add") ?: "&aYou added %amount% of backpack items to %player%.").colorHex,
            backpackRemove = (resource.getString("backpack-remove") ?: "&aYou removed %amount% of backpack items from %player%.").colorHex,
            backpackSet = (resource.getString("backpack-set") ?: "&aYou set the backpack items of %player% to %amount%.").colorHex,
        )
    }
}