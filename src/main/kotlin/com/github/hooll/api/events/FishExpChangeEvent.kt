package com.github.hooll.api.events

import com.github.hooll.api.data.PlayerData
import org.bukkit.entity.Player
import taboolib.platform.type.BukkitProxyEvent

class FishExpChangeEvent(
    val player:Player,
    val playerData: PlayerData,
    var expChange:Double,
    val type:Type
) : BukkitProxyEvent(){
    enum class Type{
        ADD,
        TAKE,
        SET
    }
    val changeAfterExp:Double = playerData.exp
}