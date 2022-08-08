package com.github.hooll.core

import com.github.hooll.api.events.FishLevelChangeEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common5.Coerce
import taboolib.module.chat.colored
import taboolib.module.kether.KetherShell
import taboolib.platform.compat.replacePlaceholder

object LevelReward {
    var levelReward = mutableMapOf<String,List<String>>()

    @SubscribeEvent
    fun onLevelReward(e: FishLevelChangeEvent) {
        levelReward.keys.forEach {
            val rewardLevel = Coerce.toInteger(it)
            if (e.changeAfterLevel == rewardLevel) {
                execute(e.player, levelReward[it]!!)
            }
        }
    }

    private fun execute(player: Player,reward: List<String>){
        reward.forEach {
            if (it.startsWith("[kether]")){
                KetherShell.eval(it.substringAfter("[kether]"))
            }else if (it.startsWith("[command]")){
                Bukkit.dispatchCommand(player,it.substringAfter("[command]"))
            }else if (it.startsWith("[console]")){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),it.substringAfter("[console]").replacePlaceholder(player))
            }else if (it.startsWith("[message]")){
                player.sendMessage(it.substringAfter("[message]").colored())
            }
        }
    }
}