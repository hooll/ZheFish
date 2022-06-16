package com.github.hooll.data

import com.github.hooll.api.ZheFishApi
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.library.xseries.getItemStack
import taboolib.library.xseries.setItemStack
import taboolib.module.configuration.createLocal
import taboolib.module.configuration.util.getMap

object DataYml {
    private val rodYml by lazy{ createLocal("rods.yml") }
    private val fishYml by lazy{ createLocal("fishes.yml") }

    @Awake(LifeCycle.LOAD)
    fun load() {
        //鱼竿获取
        rodYml.getKeys(false).forEach {
            val rodItemStack = rodYml.getItemStack(it)!!
            val map = rodYml.getMap<String, Int>("${it}.fishing-list").toMutableMap()
            val rodData = RodData(it, rodItemStack,map)
            ZheFishApi.rods.add(rodData)
        }
       //物品获取
        fishYml.getKeys(false).forEach {
            val price = fishYml.getDouble("$it.price")
            val fishData = FishData(it, fishYml.getItemStack(it)!!, price)
            ZheFishApi.fishes.add(fishData)
        }
    }

    @Awake(LifeCycle.DISABLE)
    fun save(){
        //鱼竿储存
        rodYml.clear()
        ZheFishApi.rods.forEach {
            rodYml.setItemStack(it.name, it.itemStack)
            it.fishingData.forEach { (fishData, int) ->
                rodYml["${it.name}.fishing-list.${fishData}"] = int
            }
        }
        //物品储存
        fishYml.clear()
        ZheFishApi.fishes.forEach {
            fishYml.setItemStack(it.name, it.itemStack)
            fishYml["${it.name}.price"] = it.price
        }
    }

    fun reload(){
        rodYml.reload()
        fishYml.reload()
        ZheFishApi.rods.clear()
        ZheFishApi.fishes.clear()
        load()
    }
}