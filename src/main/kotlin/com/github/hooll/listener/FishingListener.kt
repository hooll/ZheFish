package com.github.hooll.listener

import com.github.hooll.core.FishItem
import org.bukkit.entity.Item
import org.bukkit.event.player.PlayerFishEvent
import taboolib.common.platform.event.SubscribeEvent

object FishingListener {

    @SubscribeEvent
    fun onFishing(e:PlayerFishEvent){
        val player = e.player
        if (!checkFish(e)){
            return
        }
        val fish = (e.caught as Item)
        val giveItem = FishItem.randomFishItem(fish.itemStack, player)

        fish.itemStack = giveItem
    }

    private fun checkFish(e: PlayerFishEvent):Boolean{
        if (e.state != PlayerFishEvent.State.CAUGHT_FISH){
            return false
        }
        if (e.caught !is Item){
            return false
        }
        return true
    }
}