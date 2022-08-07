package com.github.hooll.config

import com.github.hooll.api.ZheFishApi
import com.github.hooll.api.data.FishData
import com.github.hooll.util.toDataByDataBase
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.getDataFolder
import taboolib.common.platform.function.releaseResourceFile
import taboolib.library.xseries.getItemStack
import taboolib.library.xseries.setItemStack
import taboolib.module.configuration.Configuration
import taboolib.platform.util.onlinePlayers
import java.io.File

object DataYML {
    //private val fishYml by lazy{ createLocal("fishes.yml") }
    private val fishYml by lazy {
        val fishYml = File(getDataFolder(), "fishes.yml")
        if (!fishYml.exists()) {
            listOf(
                "fishes.yml"
            ).forEach { releaseResourceFile(it, true) }
        }
        Configuration.loadFromFile(fishYml)
    }

    @Awake(LifeCycle.LOAD)
    fun load() {
       //物品获取
        fishYml.getKeys(false).forEach {
            val fishItemStack = fishYml.getItemStack("$it.item")!!
            val price = fishYml.getDouble("$it.price")
            val canSell = fishYml.getBoolean("$it.canSell")
            val fishData = FishData(it, fishItemStack, price,canSell)
            ZheFishApi.fishes.add(fishData)
        }
    }

    @Awake(LifeCycle.DISABLE)
    fun save(){
        //物品储存
        fishYml.clear()
        ZheFishApi.fishes.forEach {
            fishYml.setItemStack("${it.name}.item", it.itemStack)
            fishYml["${it.name}.price"] = it.price
            fishYml["${it.name}.canSell"] = it.canSell
        }
    }

    fun reload(){
        fishYml.reload()
        ZheFishApi.fishes.clear()
        load()
        ZheFishApi.players.clear()
        onlinePlayers.forEach {
            ZheFishApi.players.add(it.toDataByDataBase())
        }
    }
}