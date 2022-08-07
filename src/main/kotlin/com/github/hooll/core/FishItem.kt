package com.github.hooll.core

import com.github.hooll.api.ZheFishApi
import com.github.hooll.api.data.PlayerData
import com.github.hooll.config.Config
import ink.ptms.um.Mythic
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common5.Coerce
import taboolib.common5.RandomList
import taboolib.platform.compat.replacePlaceholder

object FishItem {

    val fishItems =mutableMapOf<String,List<String>>()

    fun randomFishItem(itemStack:ItemStack,player: Player): ItemStack {
        val playerData = ZheFishApi.getPlayerData(player)!!
        val level = playerData.level

        fishItems.keys.forEach {
            if (it.split("-").size == 2){
                val min = Coerce.toInteger(it.split("-")[0])
                val max = Coerce.toInteger(it.split("-")[1])
                if(level in min..max){
                    return getItem(itemStack,player,playerData,fishItems[it]!!)
                }
            }else{
                if(level == Coerce.toInteger(it)){
                    return getItem(itemStack,player,playerData,fishItems[it]!!)
                }
            }
        }
        playerData.addExp(Config.default_FishExp)
        return itemStack
    }

    private fun getItem(itemStack:ItemStack,player: Player,playerData: PlayerData,items:List<String>):ItemStack{
        val random = RandomList<String>()
        items.forEach {
            random.add(it,Coerce.toInteger(it.split("::")[1]))
        }
        val str = random.random()!!
        if (str.startsWith("[MythicMob]")){
            val itemInfo = str.substringAfter("[MythicMob]")
            val itemName = itemInfo.split("::")[0]
            val itemExp = Coerce.toDouble(itemInfo.split("::")[2])
            Mythic.API.getItemStack(itemName)?.let {
                playerData.addExp(itemExp)
                return it
            }
        }else if (str.startsWith("[ZheFish]")){
            val itemInfo = str.substringAfter("[ZheFish]")
            val itemName = itemInfo.split("::")[0]
            val itemExp = Coerce.toDouble(itemInfo.split("::")[2])
            ZheFishApi.getFish(itemName)?.let {
                playerData.addExp(itemExp)
                return it.getItem()
            }
        }else if (str.startsWith("[Console]")){
            val itemInfo = str.substringAfter("[Console]")
            val itemName = itemInfo.split("::")[0]
            val itemExp = Coerce.toDouble(itemInfo.split("::")[2])
            playerData.addExp(itemExp)
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),itemName.replacePlaceholder(player))
            return itemStack
        }
        playerData.addExp(Config.default_FishExp)
        return itemStack
    }

}