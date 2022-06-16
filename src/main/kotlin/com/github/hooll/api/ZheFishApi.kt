package com.github.hooll.api

import com.github.hooll.data.FishData
import com.github.hooll.data.RodData
import org.bukkit.inventory.ItemStack

object ZheFishApi {

    val rods = ArrayList<RodData>()
    val fishes = ArrayList<FishData>()

    fun getRod(name: String): RodData? {
        return rods.firstOrNull { it.name == name }
    }

    fun getFish(name: String): FishData? {
        return fishes.firstOrNull { it.name == name }
    }

    fun getRod(itemStack: ItemStack): RodData? {
        return rods.firstOrNull { it.itemStack == itemStack }
    }

    fun getFish(itemStack: ItemStack): FishData? {
        return fishes.firstOrNull { it.itemStack == itemStack }
    }

    fun getRodItemStack(name: String): ItemStack?{
        return getRod(name)?.itemStack
    }

     fun getFishItemStack(name: String): ItemStack?{
        return getFish(name)?.itemStack
    }

    fun setPrice(name: String, price:Double){
        getFish(name)?.price = price
    }

    fun setFishing(name: String,fishingData: MutableMap<String,Int>){
        getRod(name)?.fishingData = fishingData
    }

    fun checkFishingRod(itemStack: ItemStack):Boolean{
        return rods.map { it.itemStack }.contains(itemStack)
    }

}
