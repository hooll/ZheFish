package com.github.hooll.listener

import com.github.hooll.api.events.FishExpChangeEvent
import com.github.hooll.util.info
import taboolib.common.platform.event.SubscribeEvent

object LevelExpMessageListener {

    @SubscribeEvent
    fun expChange(e:FishExpChangeEvent){
        val player = e.player
        if (e.type == FishExpChangeEvent.Type.ADD){
            player.info("Info-Exp-Change", "增加",e.expChange,e.changeAfterExp,e.playerData.nextLevelExp)
        }else if (e.type == FishExpChangeEvent.Type.TAKE){
            player.info("Info-Exp-Change", "减少",e.expChange,e.changeAfterExp,e.playerData.nextLevelExp)
        }
    }

//    @SubscribeEvent
//    fun levelChange(e:FishLevelChangeEvent){
//        val player = e.player
//        if (e.type == FishLevelChangeEvent.Type.ADD){
//            player.info("Info-Level-Change", "增加",e.levelChange,e.changeAfterLevel)
//        }else if (e.type == FishLevelChangeEvent.Type.TAKE){
//            player.info("Info-Level-Change", "减少",e.levelChange,e.changeAfterLevel)
//        }else if (e.type == FishLevelChangeEvent.Type.SET){
//            player.info("Info-Level-Change", "设置",e.levelChange,e.changeAfterLevel)
//        }
//    }

}