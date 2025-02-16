package com.zorbeytorunoglu.harvester_hoe.service

import com.zorbeytorunoglu.harvester_hoe.Core

class Services(core: Core) {

    val economyService: EconomyService = EconomyService(core.hookManager.vaultHook)

    val enhancementService: EnhancementService = EnhancementService(core.playerDataManager)

    val tokenService: TokenService = TokenService(core.playerDataManager)

    val playerDataService: PlayerDataService = PlayerDataService(core.playerDataManager)

}