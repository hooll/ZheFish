package com.github.hooll.api.events

import com.github.hooll.api.data.PlayerData
import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

class FishLevelChangeEvent(
    val player:Player,
    val playerData: PlayerData,
    var levelChange:Int,
    val type:Type
) : BukkitProxyEvent(){
    enum class Type{
        ADD,
        TAKE,
        SET
    }
    val changeAfterLevel:Int = playerData.level
}