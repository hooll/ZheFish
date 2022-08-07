package com.github.hooll.api.events

import com.github.hooll.api.data.PlayerData
import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

class FishLevelUpEvent(
    val player:Player,
    val playerData: PlayerData,
    var levelBefore:Int,
) : BukkitProxyEvent(){
    val changeAfterLevel:Int = playerData.level
    val nextLevelExp = playerData.nextLevelExp
}