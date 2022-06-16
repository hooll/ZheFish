package com.github.hooll.core

import com.github.hooll.api.ZheFishApi
import com.github.hooll.data.FishData
import com.github.hooll.info
import org.bukkit.entity.Item
import org.bukkit.event.player.PlayerFishEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common5.RandomList
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.buildItem

object Fishing {

    @SubscribeEvent
    fun onFishing(e:PlayerFishEvent){
        val player = e.player
        if (e.state != PlayerFishEvent.State.CAUGHT_FISH){
            return
        }
        if (e.caught !is Item){
            return
        }
        val fish = (e.caught as Item)
        if (!ZheFishApi.checkFishingRod(player.inventory.itemInMainHand)){
            return
        }
        val useRod = ZheFishApi.getRod(player.inventory.itemInMainHand)
        val random = RandomList<String>()
        var num= 0
        useRod?.fishingData?.forEach { (fishData, int) ->
            random.add(fishData,int)
            num+=int
        }
        if (num<100){
            val int = 100-num
            random.add("Nothing",int)
        }
        val fishData = random.random()
        if (fishData.equals("Nothing")){
            player.info("Info-FishNothing")
            return
        }
        val fishName = ZheFishApi.getFish(fishData!!)
        fish.itemStack = fishName?.itemStack!!
        player.info("Info-FishMessage", fishName.name)
    }
}