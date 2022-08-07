package com.github.hooll.listener

import com.github.hooll.api.ZheFishApi
import com.github.hooll.util.toDataByDataBase
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.expansion.releaseDataContainer

object DataBaseListener {

    @SubscribeEvent
    fun onJoin(e:PlayerJoinEvent){
        //e.player.setupDataContainer()
        ZheFishApi.players.add(e.player.toDataByDataBase())

    }

    @SubscribeEvent
    fun onQuit(e:PlayerQuitEvent){
        ZheFishApi.players.remove(ZheFishApi.getPlayerData(e.player))
        e.player.releaseDataContainer()
    }

    @SubscribeEvent
    fun onKick(e:PlayerKickEvent){
        ZheFishApi.players.remove(ZheFishApi.getPlayerData(e.player))
        e.player.releaseDataContainer()
    }
}