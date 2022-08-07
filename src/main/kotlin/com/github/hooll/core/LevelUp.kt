package com.github.hooll.core

import com.github.hooll.api.events.FishExpChangeEvent
import com.github.hooll.api.events.FishLevelUpEvent
import com.github.hooll.util.info
import taboolib.common.platform.event.SubscribeEvent

object LevelUp {

    @SubscribeEvent
    fun getExp(e:FishExpChangeEvent){
        if (e.changeAfterExp < e.playerData.nextLevelExp){
            return
        }
        val upBeforeLevel = e.playerData.level
        e.playerData.addLevel(1)
        e.playerData.setExp(e.changeAfterExp - e.playerData.nextLevelExp)
        e.playerData.nextLevelExp = NextLevelExp.getExpToNextLevel(e.playerData.level)
        FishLevelUpEvent(e.player,e.playerData,upBeforeLevel).call()
        e.player.info("Info-Level-Up",e.playerData.level)
    }

}