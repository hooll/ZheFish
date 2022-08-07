package com.github.hooll.api.data

import com.github.hooll.api.events.FishExpChangeEvent
import com.github.hooll.api.events.FishLevelChangeEvent
import org.bukkit.entity.Player
import taboolib.expansion.getDataContainer
import taboolib.expansion.releaseDataContainer
import taboolib.expansion.setupDataContainer

class PlayerData (
    val player: Player,
    var level: Int,
    var exp: Double,
    var nextLevelExp: Double,
){
    @JvmName("setLevel1")
    fun setLevel(level: Int) {
        this.level = level
        save()
        FishLevelChangeEvent(player, this,level,FishLevelChangeEvent.Type.SET).call()
    }

    @JvmName("setExp1")
    fun setExp(exp: Double) {
        this.exp = exp
        save()
        FishExpChangeEvent(player, this,exp,FishExpChangeEvent.Type.SET).call()
    }

    //增加等级
    fun addLevel(level: Int){
        this.level += level
        save()
        FishLevelChangeEvent(player,this, level,FishLevelChangeEvent.Type.ADD).call()
    }
    //增加经验
    fun addExp(exp: Double){
        this.exp += exp
        save()
        FishExpChangeEvent(player,this,exp,FishExpChangeEvent.Type.ADD).call()
    }
    //减少等级
    fun takeLevel(level: Int){
        this.level -= level
        save()
        FishLevelChangeEvent(player,this, level,FishLevelChangeEvent.Type.TAKE).call()
    }
    //减少等级
    fun takeExp(exp: Double){
        this.exp -= exp
        save()
        FishExpChangeEvent(player,this,exp,FishExpChangeEvent.Type.TAKE).call()
    }

    private fun save(){
        player.releaseDataContainer()
        player.setupDataContainer()
        player.getDataContainer()["level"] = level
        player.getDataContainer()["exp"] = exp
    }
}