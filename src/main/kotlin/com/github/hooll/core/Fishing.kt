package com.github.hooll.core

import com.github.hooll.api.ZheFishApi
import com.github.hooll.data.RodData
import com.github.hooll.info
import com.github.hooll.util.Config
import org.bukkit.entity.Item
import org.bukkit.event.player.PlayerFishEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common5.RandomList

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
        //获取鱼竿
        val useRod = ZheFishApi.getRod(player.inventory.itemInMainHand) ?: RodData("NotUseTheRod",player.inventory.itemInMainHand,Config.notUseTheRod)
        //随机
        val random = RandomList<String>()
        var num= 0
        useRod.fishingData.forEach { (fishData, int) ->
            random.add(fishData,int)
            num+=int
        }
        if (num<100){
            val int = 100-num
            random.add("Nothing",int)
        }
        val fishData = random.random()
        //获取鱼
        val fishName = ZheFishApi.getFish(fishData!!)
        if (fishData == "Nothing" || fishName == null){
            player.info("Info-FishNothing")
            return
        }
        //奖励设置
        fish.itemStack = fishName.itemStack
        player.info("Info-FishMessage", fishName.name)
    }
}